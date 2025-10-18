package src.main.java.com;

public class ChoreService {
    public void completeChore(Child child, Chore chore) {
        child.completeChore(chore);
    }
    public void verifyChore(Parent parent, Chore chore, Child child) {
        parent.verifyChore(chore, child);
    }
    // public List<Chore> getAvailableChoresForChild(Child child) {

    // }
}