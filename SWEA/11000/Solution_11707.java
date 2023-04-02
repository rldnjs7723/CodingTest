

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * SWEA 11707번 오타 교정 시스템
 * 문제 분류: 자료구조 (HashMap, HashSet)
 * @author Giwon
 */
public class Solution_11707 {
	private static int n, m;
	
	private final static UserSolution usersolution = new UserSolution();
	
	private static char[][] words = new char[4000][11];
	
	private static int mstrcmp(char[] a, char[] b)
	{
		int i;
		for (i = 0; a[i] != '\0'; i++)
		{
			if (a[i] != b[i])
				return a[i] - b[i];
		}
		return a[i] - b[i];
	}

	private static void String2Char(String s, char[] b) {
		int n = s.length();
		for (int i = 0; i < n; ++i) {
			b[i] = s.charAt(i);
		}
		b[n] = '\0';
	}
	
	private static void inputWords(int wordCnt, Scanner sc) {
		
		for (int i = 0; i < wordCnt; ++i) {
			String2Char(sc.next(), words[i]);	
		}
	}
	
	private static boolean run(int m, Scanner sc) {
		
		boolean accepted = true;
		char[][] correctWord = new char[5][11];
		char[][] answerWord = new char[5][11];
		
		while(m-- > 0) {
			
			int id, timestamp, correctWordN, answerWordN;
			int wordIdx;
			
			id = sc.nextInt();
			timestamp = sc.nextInt();
			wordIdx = sc.nextInt();
			
			correctWordN = usersolution.search(id, timestamp, words[wordIdx], correctWord);
			
			answerWordN = sc.nextInt();
			
			for (int i = 0; i < answerWordN; ++i) {
				String2Char(sc.next(), answerWord[i]);
			}
			
			if (correctWordN != answerWordN) {				
				accepted = false;
			} else {
				for (int i = 0; i < answerWordN; ++i) {
					boolean isExist = false;
					
					for (int j = 0; j < correctWordN ; ++j) {
						if (mstrcmp(answerWord[i], correctWord[j]) == 0) {
							isExist = true;
						}
					}
					
					if (!isExist) {
						accepted = false;
					}
				}
			}
		}
		
		return accepted;
	}
	
	public static void main(String[] args) throws Exception {
		
		int test, T;
		int wordCnt;
		
		 System.setIn(new java.io.FileInputStream("sample_input.txt"));
		
		Scanner sc = new Scanner(System.in);
		
		T = sc.nextInt();
		
		for (test = 1 ; test <= T ; ++test) {
			
			wordCnt = sc.nextInt();
			
			inputWords(wordCnt, sc);
			
			n = sc.nextInt();
			m = sc.nextInt();
			
			usersolution.init(n);
			
			if (run(m, sc)) {
				System.out.println("#" + test + " 100");
			} else {
				System.out.println("#" + test + " 0");
			}
		}
	}
	
	public static class UserSolution {
		public static final int MAX_LENGTH = 11;
		public static final int TIMEOUT = 10;
		public static final int TYPO_COUNT = 3;
			
		class prevSearch {
			// 이전에 검색한 시간
			int timestamp;
			// 이전에 검색한 단어
			String word;
			
			public prevSearch(int timestamp, String word) {
				this.timestamp = timestamp;
				this.word = word;
			}
		}
		
		@SuppressWarnings("serial")
		class TypoMap extends HashMap<String, Set<Integer>> {}
		
		Map<Integer, prevSearch> searchRecord;
		Map<String, TypoMap> typoDictionary;
		
		void init(int n) {
			searchRecord = new HashMap<>();
			typoDictionary = new HashMap<>();
		}
		
		int search(int mId, int searchTimestamp, char[] searchWord, char[][] correctWord) {
			String word = charToString(searchWord);
			
			int count = 0;
			if(typoDictionary.containsKey(word)) {
				TypoMap typoCheck = typoDictionary.get(word);
				for(Entry<String, Set<Integer>> entry: typoCheck.entrySet()) {
					if(entry.getValue().size() >= TYPO_COUNT) {
						correctWord[count++] = StringToChar(entry.getKey());
					}
				}
			}
			
			// 이전 검색 기록이 없다면 기록
			if(!searchRecord.containsKey(mId)) {
				searchRecord.put(mId, new prevSearch(searchTimestamp, word));
				return count;
			}
			
			// 10초 이내에 검색 기록이 없다면 기록
			if(searchTimestamp - searchRecord.get(mId).timestamp > TIMEOUT) {
				searchRecord.get(mId).timestamp = searchTimestamp;
				searchRecord.get(mId).word = word;
				return count;
			}
			
			// 오타 단어 매핑에 추가
			String typo = searchRecord.get(mId).word;
			
			// 오타가 아니라면 생략
			if(!typoChecker(word, typo)) return count;
			
			TypoMap mapping = null;
			if(typoDictionary.containsKey(typo)) {
				mapping = typoDictionary.get(typo);
			} else {
				mapping = new TypoMap();
				typoDictionary.put(typo, mapping);
			}
			
			Set<Integer> mappingSet = null;
			if(mapping.containsKey(word)) {
				mappingSet = mapping.get(word);
			} else {
				mappingSet = new HashSet<>();
				mapping.put(word, mappingSet);
			}
			mappingSet.add(mId);
			
			// 검색 기록에 추가
			searchRecord.put(mId, new prevSearch(searchTimestamp, word));
			return count;
		}
		
		String charToString(char[] word) {
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while (word[i] != '\0') {
				sb.append(word[i]);
				i++;
			}
			
			return sb.toString();
		}
		
		char[] StringToChar(String word) {
			char[] result = new char[MAX_LENGTH];
			int i = 0;
			for(i = 0; i < word.length(); i++) {
				result[i] = word.charAt(i);
			}
			result[i] = '\0';
			
			return result;
		}
		
		boolean typoChecker(String word, String typo) {
			// 글자수 차이가 2 이상이라면 아예 다른 단어
			if(Math.abs(word.length() - typo.length()) >= 2) return false;
			
			// 추가인 경우 탐색
			int count, wordIdx, typoIdx;
			if(word.length() < typo.length()) {
				count = wordIdx = typoIdx = 0;
				
				while(wordIdx < word.length() && typoIdx < typo.length()) {
					if(word.charAt(wordIdx) != typo.charAt(typoIdx)) {
						count++;
						// 다른 글자수가 2 이상이라면 다른 단어
						if(count >= 2) break;
						typoIdx++;
						continue;
					}
					
					// 다음 위치로 이동
					wordIdx++;
					typoIdx++;
				}
				
				// 마지막 글자 체크
				if(count == 0) return true;
				// 다른 글자수가 1개라면 오타
				if(count == 1) return true;
			}
			
			// 치환인 경우 탐색
			if(word.length() == typo.length()) {
				count = wordIdx = typoIdx = 0;
				
				while(wordIdx < word.length() && typoIdx < typo.length()) {
					if(word.charAt(wordIdx) != typo.charAt(typoIdx)) {
						count++;
						// 다른 글자수가 2 이상이라면 다른 단어
						if(count >= 2) break;
					}
					
					// 다음 위치로 이동
					wordIdx++;
					typoIdx++;
				}
				
				// 다른 글자수가 1개라면 오타
				if(count == 1) return true;
			}
			
			// 삭제인 경우 탐색
			if(word.length() > typo.length()) {
				count = wordIdx = typoIdx = 0;
				
				while(wordIdx < word.length() && typoIdx < typo.length()) {
					if(word.charAt(wordIdx) != typo.charAt(typoIdx)) {
						count++;
						// 다른 글자수가 2 이상이라면 다른 단어
						if(count >= 2) break;
						wordIdx++;
						continue;
					}
					
					// 다음 위치로 이동
					wordIdx++;
					typoIdx++;
				}
				
				// 마지막 글자 체크
				if(count == 0) return true;
				// 다른 글자수가 1개라면 오타
				if(count == 1) return true;
			}
			
			// 세 가지 모든 경우가 아니라면 다른 단어
			return false;
		}
	}
}
