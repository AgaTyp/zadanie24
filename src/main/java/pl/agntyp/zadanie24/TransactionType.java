package pl.agntyp.zadanie24;

public enum TransactionType {
    INCOME("Przychód"),
    OUTCOME("Wydatek");

    private final String description;

    TransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TransactionType fromDescription(String description) {
        TransactionType[] values = values();
        for (TransactionType transactionType : values) {
            if (transactionType.getDescription().equals(description)) {
                return transactionType;
            }
        }
        return null;
    }
}
