import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Test {

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("res/actual_output.txt"));
		System.setIn(new FileInputStream("res/output.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input;
		List<Long> myOutput = new ArrayList<>();
		while((input = br.readLine()) != null) {
			myOutput.add(Long.parseLong(input));
		}
		
		br.close();
		
		System.setIn(new FileInputStream("res/correct_output.txt"));
		br = new BufferedReader(new InputStreamReader(System.in));
		
		List<Long> realOutput = new ArrayList<>();
		while((input = br.readLine()) != null) {
			realOutput.add(Long.parseLong(input));
		}
		
		for(int i = 0; i < realOutput.size(); i++) {
			if(myOutput.get(i).longValue() != realOutput.get(i).longValue()) {
				System.out.println(i + " " + myOutput.get(i) + " " + realOutput.get(i));
			}
		}
	}

}
