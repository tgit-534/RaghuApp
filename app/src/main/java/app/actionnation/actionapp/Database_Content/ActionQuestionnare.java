package app.actionnation.actionapp.Database_Content;

public class ActionQuestionnare {

    public ActionQuestionnare() {
    }

    String ActionQuestion;
    int Status;
    int QuestionNumber;

    public String getActionQuestion() {
        return ActionQuestion;
    }

    public void setActionQuestion(String actionQuestion) {
        ActionQuestion = actionQuestion;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getQuestionNumber() {
        return QuestionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        QuestionNumber = questionNumber;
    }
}
