
import java.math.BigInteger;
import java.util.*;
public class Calculator {
	private String 		expr;
	private ArrayList 	afterExpr;
	private int    		index;         //index of the current char at expr
	private Stack<Character> 	stack;
	private Stack<BigInteger>   bigIntegerStack;
	public Calculator()
	{
		index   	= 0;
		afterExpr 	= new ArrayList();
		expr 		= new String();
		stack 		= new Stack();
	}	

	public int exec(String expression)
	{
		BigInteger result;
		if(isBlankLine(expression) )
		{
			return -1;
		}
		expr = expression;		 
		stack.clear();
		afterExpr.clear();
		index = 0;
		try{
			changeMiddleToAfter();
		}catch(NullPointerException ne){
			System.exit(1);
		}
		stack.clear();
		if((result = calculateExpr()) != null)
		{
			System.out.println(result);
		}
		return 0;
	}
	private boolean isBlankLine(String line)
	{
		for(int i = 0; i < line.length(); ++i)
		{
			char c = line.charAt(i);
			if(c != ' ' || c != '\t' || c != '\n')
				return false;
		}
		return true;
	}
	private BigInteger calculateExpr()
	{
		BigInteger curResult = new BigInteger("1");
		bigIntegerStack = new Stack<BigInteger>();
		for(int i = 0; i < afterExpr.size(); ++i)
		{
			Class cl = afterExpr.get(i).getClass();
			if(cl.getName().equals("java.math.BigInteger"))
			{
				bigIntegerStack.push((BigInteger)afterExpr.get(i));
			}
			else
			{
				BigInteger op1, op2;
				try{
					op1 = bigIntegerStack.pop();
					op2 = bigIntegerStack.pop();
					switch((char) afterExpr.get(i))
					{
						case '+':
							curResult = op1.add(op2);
							break;
						case '-':
							curResult = op2.subtract(op1);
							break;
						case '%':
							curResult = op2.mod(op1);
							break;
						case '*':
							curResult = op2.multiply(op1);
							break;
						case '/':
							curResult = op2.divide(op1);
							break;
						default :
							System.out.println("error: undefined operator!!");
							return null;
					}
				}catch(EmptyStackException ee){
					System.out.println("表达式语法错误，操作数数目不符合规范！");
					System.exit(1);
				}catch(ArithmeticException ae){
					System.out.println("除数为零，或者被模数为零！");
					System.exit(1);
				}
				bigIntegerStack.push(curResult);
			}
		}
		if(bigIntegerStack.size() != 1)
		{
			System.out.println("error: invalid expr !!");
			return null;
		}else{
			return bigIntegerStack.pop();
		}
		
	}

	public void printExpr()
	{
		for(int i = 0; i < afterExpr.size(); ++i)
		{
			System.out.print(afterExpr.get(i)+" ");
		}
	}
	public BigInteger getNumber()
	{
		BigInteger number = null;
		String numberString = new String("");
		if(expr.charAt(index) == '-')
		{
			numberString += '-';
			index++;
		}
		for(; index < expr.length(); ++index)
		{
			char c = expr.charAt(index);
			//negative
			if(isNumber(c))
			{
				numberString += c;
			}
			else break;
		}
		try
		{
			number = new BigInteger(numberString);
			
		}
		catch (NumberFormatException ne)
		{
			System.out.println("Number Format error!!");
		}
		return number;
	}
	
	private boolean isNumber(char token)
	{
		return token <= '9' && token >= '0';
	}
	private boolean isOperator(char token)
	{
		return token == '+' || token == '-' || token == '/' || token == '*' || token == '%';
	}
	private boolean isBracket(char token)
	{
		return token == '(' || token == ')';
	}
	private int getOperatorPriority(char operator)
	{
		switch(operator)
		{
			case '+':
			case '-':
				return 1;
			case '/':
			case '*':
			case '%':
				return 2;				
			default:
				return 0;
		}
	}
	private boolean isBigger(char s, char t)
	{
		return getOperatorPriority(s) > getOperatorPriority(t); 
	}
	private void changeMiddleToAfter()
	{
		char preToken = '\0';
		for(; index < expr.length(); ++index)
		{
			char curToken = expr.charAt(index);
			if(curToken == ' ')
				continue;
			if(isNumber(curToken) || curToken == '-' && !isNumber(preToken) && preToken != ')' && preToken != '\0')
			{
				BigInteger number = getNumber();
				if(number == null)
				{
					throw(new NullPointerException());
				}
				afterExpr.add(number);
				--index;
			}
			else if(isOperator(curToken))
			{
				if(stack.size() == 0 || isBigger(curToken, stack.peek()) || isBracket(stack.peek()) ) 
				{
					stack.push(curToken);
				}
				else if ( !isBigger(curToken, stack.peek()) )
				{
					while(stack.size() > 0 && !isBigger(curToken, stack.peek()) && stack.peek() != '(' )
					{
						try{
							afterExpr.add( stack.pop() );
						}catch(EmptyStackException ee){
							System.out.println("操作数语法错误！");
							System.exit(1);
						}
					}
					stack.push(curToken);
				}
				
			}
			else if(isBracket(curToken))
			{
				if(curToken == '(')
				{
					stack.push(curToken);
				}
				else
				{
					try{
						while(stack.peek() != '(')
						{
							afterExpr.add( stack.pop() );
						}
						stack.pop();
					}catch(EmptyStackException ee){
						System.out.println("括号语法错误！");
						System.exit(1);
					}
				}	
			}
			else
			{
				System.out.println("表达式中有未定义的操作符！");
				System.exit(1);
			}
			preToken = curToken;
		}
		while(stack.size() != 0)
		{
			afterExpr.add(stack.pop());
		}
		
	}
	
	public static void main(String[] args) 
	{
		Calculator calc = new Calculator();
		Scanner scanner = new Scanner(System.in);
		String expr 	= new String();
		while(scanner.hasNext())
		{
			expr = scanner.nextLine();
			if(expr.equals("quit"))
			{
				break;
			}
			calc.exec(expr);
			
		}
		scanner.close();
		
	}

}

