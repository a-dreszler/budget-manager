package pl.adreszler.budgetmanager;

enum Type {
    EXPENDITURE("Expenditure"),
    INCOME("Income");

    private final String nameEn;

    Type(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEn() {
        return nameEn;
    }
}
