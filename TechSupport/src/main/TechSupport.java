package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TechSupport {
	HashMap<String, ArrayList<String>> map;
	ArrayList<String> keyword_list;
	private void initKeyword(String file_name) throws IOException
	{
		InputStream f;
		String keyword;
		String sentence;
		ArrayList<String> array;
		int num;
		try {
			f = new FileInputStream(file_name);
			Scanner scanner = new Scanner(f);
			while(scanner.hasNext())
			{
				array 	= new ArrayList<String>();
				keyword = scanner.next();
				keyword_list.add(keyword);
				num 	= scanner.nextInt();
				scanner.nextLine();
				int i;
				for(i = 0; i < num && scanner.hasNext(); ++i)
				{
					sentence = scanner.nextLine();
					array.add(sentence);
				}
				map.put(keyword, array);
			}
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Temperally stop this service!Very SORRY!");
		}
	}
	
	private void giveResponceNoKeyword()
	{
		System.out.println("What's your main pointer?");
	}
	
	private void giveResponce(String word)
	{
		Random random = new Random();
		ArrayList<String> array = map.get(word); 
		int len = array.size();
		System.out.println(array.get(random.nextInt(len-1)));
	}
	private boolean isWordChar(char c)
	{
		return c >='a' && c <= 'z' || c >='A' && c <= 'Z';
	}
	
	private void preProcess(String[] input_word)
	{
		String input;
		int begin;
		int end;
		for(int i = 0; i < input_word.length; ++i)
		{
			input = input_word[i];
			end = begin = 0;
			for(int j = 0  ; j < input.length(); ++j)
			{
				if(isWordChar(input.charAt(j)))
				{
					begin = j;
					break;
				}
			}
			for(int k = input.length()-1; k >= 0; --k)
			{
				if(isWordChar(input.charAt(k)))
				{
					end = k;
					break;
				}
			}	
			
			if(begin != end)
				input_word[i] = input.substring(begin, end+1);
		}
	}
	/**
	 * 
	 * @param input_line
	 */
	public void solve(String input_line)
	{
		String[] input_words = input_line.split(" ");
		preProcess(input_words);
		String word;
		int num_input_keyword = 0;
		for(int i = 0; i < input_words.length; ++i)
		{
			if(keyword_list.contains(input_words[i]))
			{
				giveResponce(input_words[i]);
				num_input_keyword++;
			}
		}
		if(num_input_keyword == 0)
			giveResponceNoKeyword();
	}

	TechSupport(String file_name) throws IOException
	{
		map 		 = new HashMap<String, ArrayList<String>>();
		keyword_list = new ArrayList<String>(); 
		initKeyword(file_name);		
	}
	public static void main(String[] args) throws IOException {
		TechSupport ts = new TechSupport("C:\\Users\\Administrator\\workspace\\TechSupport\\src\\main\\keyword.txt");
		Scanner scanner = new Scanner(System.in);
		String input_line;
		while(scanner.hasNext())
		{
			input_line = scanner.nextLine();
			if(input_line.equals("quit"))
				break;
			ts.solve(input_line);
		}
	}

}
