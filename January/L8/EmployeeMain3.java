package L8;

interface Account {
    int balanceEquiry();
    String miniStatemen();
    void credit(int amount);
    void withdraw(int amount);
}

class BankAccount implements Account{ 
    protected int balance;
    protected String statement="";

    public BankAccount(int balance) {
        this.balance = balance;
    }

    @Override
    public int balanceEquiry()
    {
        return balance;
    }

    @Override
    public String miniStatemen()
    {
        return statement + " | Balance: " + balance+"\n";
    }

    @Override
    public void credit(int amt)
    {
        balance += amt;
        statement  += "Credited: " + amt+"\n";
    }

    @Override
    public void withdraw(int amt)
    {
        if(amt<balance){
            balance -= amt;
            statement += "Withdrawn: " + amt+"\n";
        }
        else
        {
            System.out.println("Error insufficient funds");
        }
    }
}

class SavingsAccount extends BankAccount{
    private static final int MIN_BAL = 1000;
    public SavingsAccount(int balance)
    {
        super(balance);
    }
    @Override
    public void withdraw(int amt) {
        if (balance - amt < MIN_BAL) {
            statement = "Withdrawal denied: Min balance INR1000 required";
        } else {
            balance -= amt;
            statement = "Savings withdrawal: " + amt;
        }
    }
}

// class CurrentAccount extends BankAccount{

// }

class Employee{
    Account accountType;
    private String name,empId;
    public Employee(String name,String empId,String accType,int balance) {
        this.name =name;
        this.empId=empId;
        this.accountType = (accType=="saving")? new SavingsAccount() : new CurrentAccount(); //basically Account acc = new SavingsAccount();
    }

    public String getDetails()
    {
        return "Employee Name: " + name + ", ID: " + empId;
    }
    
}

public class EmployeeMain3 {
    public static void main(String[] args) {
        Employee emp = new Employee("BadalSingh", "E123","saving", 1000);
        System.out.println(emp.getDetails());
        System.out.println(emp.accountType.miniStatemen());
        emp.accountType.credit(500);
        System.out.println("After credit, " + emp.accountType.miniStatemen());
        emp.accountType.withdraw(300);
        System.out.println("After withdrawal, " + emp.accountType.miniStatemen());
    }
}
