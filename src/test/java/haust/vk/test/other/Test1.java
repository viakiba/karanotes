package haust.vk.test.other;

public class Test1 {
	int a ;
	int b;
	
	void initt(){
		a=1;
		b=2;
	}
	
	void outt(){
		System.out.println(a+"+"+b);
	}
	
	public static void main(String[] args) {
		Test1 t = new Test1();
		t.initt();
		t.outt();
		
		int c = 1;
		c = c--;
		System.out.println(c);
		c = --c;
		System.out.println(c);
	}
	
	
}
