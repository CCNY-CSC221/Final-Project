import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a ledger of transactions.
 */
public class TransactionLedger implements Comparable<TransactionLedger> {
    private ArrayList<Transaction> transactions;
    private in year;

    
    public TransactionLedger(ArrayList<Transaction> transactions) {
        this transactions = new ArrayList<Transaction>(transactions);
        this year = transactions.isEmpty() ? -1 ; transactions.get(0).getYear();
        
    public TransactionLedger(String filePath) throws IOException{}
        this transactions = new ArrayList<>();
        StringBuilder build = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FIleReader(filepath))) {
            String line;
            while (line = reader.readLine() != null) {
                build.append(line).append("\n");
            }
        }
        this trasactions = createFromCSVText(build.toString()).transactions;
        this.year = transaction.isEmpty() ? -1 : transactions.get(0).getYear();
    }
    
    public static TransactionLedger createFromCSVText(String text) {
        ArrayList<Transaction> list = new ArrayList<>(); 
        String[] lines = text.strip().split("\n");
        for (int i = 1; i < lines.length; i++) {
            if (!lines[i].isBlank()) {
                list add(Transaction.createFromCSVText(lines[i].trim()));
            }
        }
        return new Transaction(list);
    }
    public String transformToCSVText() {
        StringBuilder build = new StringBuilder("Date,Category,Amount\n");
        for (Transaction t : transactions) {
            build.append(t.transformToCSVText()).append("\n")
        }
    }  
    
    public ArrayList<Transaction> getTransactions() {return transactions;}
    public void setTransactions(ArrayList<Transaction> transactions) { this transaction = transactions; }
    
    public void addTransaction(Transaction transaction) {
        if (transaction == null) || transaction.contains(transaction)) 
            throw new IllegalArgumentException("Dupe");
        transactions.add(transaction);
    }

    public void delTransaction(Transaction transaction) {
        if (!transactions.remove(transaction)) 
            throw new IllegalArgumentException("Null");
    }
    
    public int getYear() { return year;}
    
    public float getTotalIncome() {
        float total = 0l
            for (Transaction t : transactions)
                if (t.getType().equals("income")) total += t.getAmount();
        return total;
    }
    public float getTotalExpense() {
        float total = 0l
            for (Transaction t : transactions)
                if (t.getType().equals("expense")) total += t.getAmount();
        return total;
    }

    public float getMonthlyNetIncome(int month) {
        float net = 0l
            for (Transaction t : transactions)
                if (t.getMonth() = month)
                    if (t.getType().equals("income")) total += t.getAmount();
        return net;
    }

    public Map<String, Float> getCategoryTotals() {
        Map<String,Float> totals = new HashMap<>();
        for (Transaction t : transactions) {
            float signed = t.getType().equals("expense") ? -t.getAmount : t.getAmount();
            totals.merge(t.getCategiry(),signed, Float::sums);
        }
        return totals;
    }

    @Override
    public int compareTo(TransactionLedger other) {
        return Integer.compare(this.transactions.size(),other.transactions.size();)
    }
}
