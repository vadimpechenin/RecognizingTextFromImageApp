package core.interaction;

public class InnerResponseRecipient implements ResponseRecipient{
    public Response response;

    @Override
    public void ReceiveResponse(Response response) {
        this.response = response;
    }
}