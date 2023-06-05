import java.util.Scanner;

/**
 * 백준 2884번 알람 시계 
 * 문제 분류: 구현
 * @author Giwon
 */
public class Main_2884 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int hour = sc.nextInt();
		final int min = sc.nextInt();
		Time time = new Time(hour, min);
		time.subMin(45);
		
		System.out.println(time);
		sc.close();
	}

	public static class Time {
		int hour, min;

		public Time(int hour, int min) {
			this.hour = hour;
			this.min = min;
		}

		public void subMin(int min) {
			this.min -= min;
			if (this.min < 0) {
				hour--;
				this.min += 60;
			}

			if (this.hour < 0) {
				hour += 24;
			}
		}

		@Override
		public String toString() {
			return this.hour + " " + this.min;
		}
	}
}
