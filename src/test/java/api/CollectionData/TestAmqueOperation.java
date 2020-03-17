package api.CollectionData;

public class TestAmqueOperation {
    public static void test_updateMt_batch_status() {
        AmqueOperation test = new AmqueOperation();
        test.updateMt_batch_status();
    }

    public static void test_deleteTodayMt_loan() {
        AmqueOperation test = new AmqueOperation();
        test.deleteTodayMt_loan();
    }

    public static void test_deleteTodayMt_customer() {
        AmqueOperation test = new AmqueOperation();
        test.deleteTodayMt_customer();
    }

    public static void test_deleteTodayMt_contact() {
        AmqueOperation test = new AmqueOperation();
        test.deleteTodayMt_contact();
    }

    public static void test_deleteAllLoan_durable() {
        AmqueOperation test = new AmqueOperation();
        test.deleteAllLoan_durable();
    }

    public static void test_deleteAllLoan_durable_md() {
        AmqueOperation test = new AmqueOperation();
        test.deleteAllLoan_durable_md();
    }

    public static void test_updateMt_collection_related_loans(Integer loan_id) {
        AmqueOperation test = new AmqueOperation();
        test.updateMt_collection_related_loans(loan_id);
    }

    public static void main(String[] args) {
//        需要插入的BI数据
        Integer loan_id = 123;
//        test_updateMt_batch_status();
//        test_deleteTodayMt_loan();
//        test_deleteTodayMt_customer();
//        test_deleteTodayMt_contact();
//        test_deleteAllLoan_durable();
//        test_deleteAllLoan_durable_md();
        test_updateMt_collection_related_loans(loan_id);
    }
}
