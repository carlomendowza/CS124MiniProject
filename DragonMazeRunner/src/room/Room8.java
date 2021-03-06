package room;

import maze.CheckEnter;
import maze.MazeMaker;
import java.io.*;
import anno.*;

@CheckEnter
public class Room8 {
	@Direction(command="edgeworthOffice")
	private Room7 room7;
	@Direction(command="room303")
	private Room9 room9;
	private String[] dialogue;
	private int dialogueCount;
	public boolean isDialogueFinished;
	private boolean usedButton;
	
	public Room8() {
		dialogue = new String[3];
		dialogueCount = 0;
		isDialogueFinished = false;

		dialogue[0] = "Phoenix: There\'s quite a lot of documents here. There seems to be a stack related to the current case. Let\'s have a look.";
		dialogue[1] = "Profile: April May (F, 23)\nHeight: 157cm.\nAn employee of Bluecorp.\nRelation to case: was in Gatewater Hotel, occupant of Room 303.";
		dialogue[2] = "Phoenix: S-she\'s kinda cute... I like her pink blouse with heart-shaped buttons... wait.\nI feel like I\'ve seen a button like that before. Could it be..?";
	}
	
	public String getDialogue() {
		if (dialogueCount >= dialogue.length) isDialogueFinished = true;
		if (isDialogueFinished) return "-- End of text --\n\nPlease enter a valid command.";
		else return dialogue[dialogueCount++];
	}
	
	public String getDescription(MazeMaker maze) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	maze.items.remove("evidenceKey");
    	pw.println("August 3, 11:00 AM - Police Station -- Evidence Room");
    	if (dialogueCount < dialogue.length) pw.println("\nPress enter to advance text.");
    	
    	return sw.toString();
	}
	
	public boolean getDialogueStatus() {
		return isDialogueFinished;
	}
	
	public String getRoomImg() {
		return "8.png";
	}
	
	@Command(command="use button")
	public String useButton(MazeMaker maze) {
		if (maze.findItem("bloodyButton")) return "Phoenix: (There's no time to waste, I should go to the murderer's hotel room. It's Room 303, Gatewater Hotel.)";
		if (usedButton) return "Phoenix: (There's no time to waste, I should use luminol on the button.)";
		else {
			usedButton = true;
			return "Phoenix: Just as I thought... it matches Ms. May\'s blouse buttons. If it was at the crime scene, though... Ah! I see!\n" +
				"The gap in the blood stain is heart-shaped. It\'s too good to be a coincidence, but maybe I should check that button with Luminol.";
		}
	}
	
	@Command(command="use luminol")
	public String useLuminol(MazeMaker maze) {
		if (usedButton) {
			if(maze.findItem("bloodyButton")) {
				return "Phoenix: (I've already sprayed the button with luminol.)";
			} else {
				if(!maze.findItem("button")) return "Phoenix: (What am I supposed to spray this at??)";
				if(!maze.findItem("button") && !maze.findItem("luminol")) return "Phoenix: (What..?)";
				
				maze.items.remove("button");
				maze.items.put("bloodyButton", "Pink, heart-shaped, and was drenched in blood.");
				return "Phoenix: The button has turned a ghastly blue. There\'s no mistaking it. This buttton and its perky owner was at the crime scene. Sorry, Ms. May, but it looks like I\'ll have to visit your room.";
			}
		} else {
			return "Phoenix: (What am I supposed to spray this at??)";
		}
	}
}
