package ph.com.swak.model;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */

@Table(name = "Employee")
public class Employee extends Model {

    @Column(name = "empId")
    @SerializedName("empid")
    public String EmpId;

    @Column(name = "pwd")
    @SerializedName("pwd")
    public String pwd;

    @Column(name = "firstname")
    @SerializedName("firstname")
    public String Firstname;

    @Column(name = "lastname")
    @SerializedName("lastname")
    public String Lastname;

    @Column(name = "middlename")
    @SerializedName("middlename")
    public String Middlename;

    @Column(name = "suffix")
    @SerializedName("suffix")
    public String Suffix;

    @Column(name = "birthdate")
    @SerializedName("birthdate")
    public String Birthdate;

    @Column(name = "age")
    @SerializedName("age")
    public String Age;

    @Column(name = "status")
    @SerializedName("civil_status")
    public String Civil;

    @Column(name = "gender")
    @SerializedName("gender")
    public String Gender;

    @Column(name = "designation")
    @SerializedName("designation")
    public String Designation;

    @Column(name = "sss")
    @SerializedName("sss")
    public String SSS;

    @Column(name = "tin")
    @SerializedName("tin")
    public String TIN;

    @Column(name = "philhealth")
    @SerializedName("philhealth")
    public String PhilHealth;

    @Column(name = "pagibig")
    @SerializedName("pagibig")
    public String PAGIBIG;

    @Column(name = "department")
    @SerializedName("department")
    public String Department;

    @Column(name = "company")
    @SerializedName("company")
    public String Company;

    @Column(name = "jobgrade")
    @SerializedName("jobgrade")
    public String JobGrade;

    @Column(name = "img")
    @SerializedName("picture")
    public String prof_pic;

    @SerializedName("result")
    public String result;

    public Employee() {
        super();
    }

    public String getProf_pic() {
        return prof_pic;
    }

    public void setProf_pic(String prof_pic) {
        this.prof_pic = prof_pic;
    }

    public String getEmpId() {
        return EmpId;
    }

    public void setEmpId(String empId) {
        EmpId = empId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getMiddlename() {
        return Middlename;
    }

    public void setMiddlename(String middlename) {
        Middlename = middlename;
    }

    public String getSuffix() {
        return Suffix;
    }

    public void setSuffix(String suffix) {
        Suffix = suffix;
    }

    public String getBirthdate() {
        return Birthdate;
    }

    public void setBirthdate(String birthdate) {
        Birthdate = birthdate;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getCivil() {
        return Civil;
    }

    public void setCivil(String civil) {
        Civil = civil;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getSSS() {
        return SSS;
    }

    public void setSSS(String SSS) {
        this.SSS = SSS;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getPhilHealth() {
        return PhilHealth;
    }

    public void setPhilHealth(String philHealth) {
        PhilHealth = philHealth;
    }

    public String getPAGIBIG() {
        return PAGIBIG;
    }

    public void setPAGIBIG(String PAGIBIG) {
        this.PAGIBIG = PAGIBIG;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getJobGrade() {
        return JobGrade;
    }

    public void setJobGrade(String jobGrade) {
        JobGrade = jobGrade;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFullname(){
        return Lastname + ", " + Firstname;
    }

    public static Employee getEmployeeInfo(){
        return new Select().from(Employee.class).limit(1).executeSingle();
    }

    public static void changepassword(String newpass){

        Employee employee = new Select().from(Employee.class).where("empId=?", Employee.getEmployeeInfo().getEmpId()).executeSingle();
        Log.i("Empid", employee.getEmpId());
        employee.setPwd(newpass);
        employee.save();
    }

    public static void signout(){
        new Delete().from(Employee.class).execute();
    }
}
