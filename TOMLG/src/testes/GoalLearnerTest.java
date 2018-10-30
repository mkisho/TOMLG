package testes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import tomlg.goallearning.ActionsEpisodeHistory;
import tomlg.goallearning.GoalLearner;

public class GoalLearnerTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		FileInputStream fileInputStream = new FileInputStream("actionHistory.txt");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		ActionsEpisodeHistory actionHistory = (ActionsEpisodeHistory) objectInputStream.readObject();
		objectInputStream.close();
		System.out.println(actionHistory);
//
//		GoalLearner goalLearner = new GoalLearner(actionHistory, null);
//
//		System.out.println(goalLearner.reasoning(1));
	}

}
