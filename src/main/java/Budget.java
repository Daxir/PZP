import java.io.Serializable;

public class Budget implements Observer, Serializable {
    private double currentSpendings;
    private final double monthlyBudget;
    private double remainingSpendings;
    private final ReceiptRepository receiptRep;

    public Budget(double monthlyBudget, ReceiptRepository receiptRep) {
        this.monthlyBudget = monthlyBudget;
        this.receiptRep = receiptRep;
        this.remainingSpendings = monthlyBudget;
        this.currentSpendings = 0.0;
    }

    public double getCurrentSpendings() {
        return currentSpendings;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public double getRemainingSpendings() {
        return remainingSpendings;
    }

    public void update() {
        currentSpendings += receiptRep.getTotalSpendings();
        remainingSpendings = monthlyBudget - currentSpendings;
    }
}
