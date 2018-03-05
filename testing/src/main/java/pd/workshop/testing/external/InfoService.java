package pd.workshop.testing.external;

public interface InfoService {
    boolean hasSubscription(String phoneNumber);
    void sendMessage(String phoneNumber, String message);
}
