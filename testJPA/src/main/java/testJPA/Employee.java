package testJPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Access;
import static javax.persistence.AccessType.FIELD;
import static javax.persistence.AccessType.PROPERTY;

@Entity(name = "teste")
@Table(name = "teste")
public class Employee {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO) 	
   
   private int eid;
   private String ename;
   private double salary;
   private String deg;
   private int confia;
   
   public Employee(int eid, String ename, double salary, String deg) {
      super( );
      this.eid = eid;
      this.ename = ename;
      this.salary = salary;
      this.deg = deg;

   }

   
   public Employee( ) {
      super();
   }

   public int getEid( ) {
      return eid;
   }
   
   public void setEid(int eid) {
      this.eid = eid;
   }
   
   public String getEname( ) {
      return ename;
   }
   
   public void setEname(String ename) {
      this.ename = ename;
   }

   public double getSalary( ) {
      return salary;
   }
   
   public void setSalary(double salary) {
      this.salary = salary;
   }

   public String getDeg( ) {
      return deg;
   }
   
   public void setDeg(String deg) {
      this.deg = deg;
   }
   public void setTest(int confia) {
	   this.confia = confia;
   }
   
   @Override
   public String toString() {
      return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + ", deg=" + deg + ", confia=" + confia + "]";
   }
}