package pl.agntyp.zadanie24;

public enum TransactionType {
    INCOME("Przychód", "przychodów"),
    OUTCOME("Wydatek", "wydatków");

    private final String description;
    private final String titlePart;

    TransactionType(String description, String titlePart) {
        this.description = description;
        this.titlePart = titlePart;
    }

    public String getDescription() {
        return description;
    }

    public String getTitlePart() {
        return titlePart;
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
