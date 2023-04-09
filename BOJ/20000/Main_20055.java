import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 백준 20055번 컨베이어 벨트 위의 로봇 
 * 문제 분류: 구현, 시뮬레이션, 자료구조(List)
 * @author Giwon
 */
public class Main_20055 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 컨베이어 벨트 길이 
		final int N = Integer.parseInt(st.nextToken());
		// 중단하기 위한 내구도 0인 칸의 개수
		final int K = Integer.parseInt(st.nextToken());
		
		// 벨트 입력
		Belt conveyorBelt = new Belt(N, K);
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 2 * N; i++) {
			conveyorBelt.addNode(Integer.parseInt(st.nextToken()));
		}
		// 벨트 노드 연결 
		conveyorBelt.link();
		// 로봇 리스트 생성 
		List<Robot> robotList = new ArrayList<>();
		
		int stage = 0;
		while(conveyorBelt.checkDurability()) {
			// 벨트 회전 
			conveyorBelt.rotate();
			// 로봇 내리기
			conveyorBelt.unload();
			// 리스트 정리
			Robot.clean(robotList);
			
			// 로봇 이동
			for(Robot robot: robotList) {
				robot.move();
				// 로봇 내리기 
				conveyorBelt.unload();
			}
			// 리스트 정리
			Robot.clean(robotList);
			
			// 로봇 올리기
			conveyorBelt.load(robotList);
			
			stage++;
		}
		
		System.out.println(stage);
		br.close();
	}

	public static class Robot {
		// 현재 벨트에 위치한 칸 
		Node curr;
		
		public Robot(Node curr) {
			this.curr = curr;
		}
		
		// 다음 칸으로 이동 
		public void move() {
			// 다음 칸에 로봇이 있는 경우 생략 
			if(curr.next.robot != null) return;
			// 다음 칸의 내구도가 1 이상이 아닌 경우 생략 
			if(curr.next.durability == 0) return;
			
			curr.next.robot = this;
			Node prev = curr;
			curr = curr.next;
			prev.robot = null;
			// 내구도 감소 
			curr.durability--;
		}
		
		// 로봇 리스트 정리
		public static void clean(List<Robot> robotList) {
			for(int i = robotList.size() - 1; i >= 0; i--) {
				if(robotList.get(i).curr == null) robotList.remove(i);
			}
		}
	}
	
	public static class Node {
		// 벨트 칸 내구도 
		int durability;
		// 현재 올라와 있는 로봇
		Robot robot;
		// 다음 벨트 칸
		Node next;
		
		public Node(int durability) {
			this.durability = durability;
			this.robot = null;
			this.next = null;
		}
	}
	
	public static class Belt {
		// 컨베이어 벨트 길이 
		int N;
		// 중단하기 위한 내구도 0인 칸의 개수
		int K;
		// 벨트 리스트 
		List<Node> conveyor;
		// 현재 로봇을 올리는 위치, 내리는 위치 Index
		int load, unload;
		
		public Belt(int N, int K) {
			this.N = N;
			this.K = K;
			this.conveyor = new ArrayList<>();
			this.load = 0;
			this.unload = N - 1;
		}
		
		// 벨트에 노드 추가 
		public void addNode(int durability) {
			conveyor.add(new Node(durability));
		}
		
		// 벨트 노드끼리 연결 
		public void link() {
			for(int i = 0; i < 2 * N; i++) {
				conveyor.get(i).next = conveyor.get((i + 1) % (2 * N));
			}
		}
		
		// 벨트 회전
		public void rotate() {
			load = (load - 1 + 2 * N) % (2 * N);
			unload = (unload - 1 + 2 * N) % (2 * N);
		}
		
		// 로봇 올리기 
		public void load(List<Robot> robotList) {
			Node loadNode =  conveyor.get(load);
			// 내구도가 0이라면 생략 
			if(loadNode.durability == 0) return;
			
			loadNode.robot = new Robot(loadNode);
			robotList.add(loadNode.robot);
			// 내구도 감소 
			loadNode.durability--;
		}
		
		// 로봇 내리기
		public void unload() throws Exception {
			Node unloadNode = conveyor.get(unload);
			// 로봇이 없다면 생략 
			if(unloadNode.robot == null) return;

			unloadNode.robot.curr = null;
			unloadNode.robot = null;
		}
		
		// 내구도 체크 
		public boolean checkDurability() {
			int count = 0;
			for(Node node: conveyor) {
				if(node.durability == 0) count++;
			}
			
			return count < K;
		}
	}
}
