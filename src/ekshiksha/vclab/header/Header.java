
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.header;

/**
 *
 * @author mayank
 */
public class Header {

    private String title;
    private String author_Name;
    private String level;
    private int marks;
    private String description;
    private String student_Name;
    private String instruction;

    public Header(String title, String author_Name, String level, int marks, String description, String student_Name, String instruction) {
        this.title= title;
        this.author_Name= author_Name;
        this.level= level;
        this.marks= marks;
        this.description= description;
        this.student_Name= student_Name;
        this.instruction=instruction;
    }

    public Header() {
    }

    public void setTitle(String title) {
        this.title =title;
    }
    public String getTitle() {
        return title;
    }

    public void setAuthor_Name(String author_Name) {
        this.author_Name = author_Name;
    }
    public String getAuthor_Name() {
        return author_Name;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }
    
    public void setMarks(int marks) {
        this.marks = marks;
    }
    public int getMarks() {
        return marks;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setStudent_Name(String student_Name) {
        this.student_Name = student_Name;
    }
    public String getStudent_Name() {
        return student_Name;
    }
    
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public String getInstruction() {
        return instruction;
    }
}
