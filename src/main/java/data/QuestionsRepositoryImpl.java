package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.QuestionsRepository;
import entities.Question;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionsRepositoryImpl extends QuestionsRepository {

    @Override
    public List<Question> getQuestions() throws IOException {

        Path path = Paths.get("src","main","resources", "questions.json");
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        Gson gson = new Gson();

        return gson.fromJson(data, new TypeToken<List<Question>>() {
        }.getType());
    }
}
