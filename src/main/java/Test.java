import java.util.Set;
import java.util.TreeSet;

public class Test {
	public static void main(String[] args) {
		Set<Student> set = new TreeSet<Student>();
		set.add(new Student(100,"zs"));
		set.add(new Student(200,"ls"));
		set.add(new Student(300,"ww"));
		set.add(new Student(100,"zs"));
		System.out.println(set.size());
	}
}

class Student implements Comparable<Student>{
	private int stdNo;
	private String name;
	
	public Student() {
	}
	
	public Student(int stdNo, String name) {
		super();
		this.stdNo = stdNo;
		this.name = name;
	}

	public int getStdNo() {
		return stdNo;
	}
	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + stdNo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stdNo != other.stdNo)
			return false;
		return true;
	}

	@Override
	public int compareTo(Student o) {
		return this.stdNo - o.stdNo;
	}
	
	
}