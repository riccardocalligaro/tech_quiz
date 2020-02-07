package domain;

import entities.Question;

import java.io.IOException;
import java.util.List;

public abstract class QuestionsRepository {
    public abstract List<Question> getQuestions() throws IOException;
}
