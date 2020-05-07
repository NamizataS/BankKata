package bank;

class Account {

    // Attributes
    // TODO
        private String name;
        private float balance;
        private float overdraft;
        private boolean lock;


    // Constructor
    // TODO
    public Account( String name, float balance, float overdraft, boolean lock ){
        this.name = name;
        this.balance = balance;
        this.overdraft = overdraft;
        this.lock = lock;
    }

    // Methods
    // TODO


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(float overdraft) {
        this.overdraft = overdraft;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", overdraft=" + overdraft +
                ", lock=" + lock +
                '}';
    }
}
