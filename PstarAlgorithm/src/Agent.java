import processing.core.PApplet;



public class Agent{
	
	int startAgentX, startAgentY, sizeAgent;
	
	
	PApplet parent;
	
	Agent(PApplet p, int sAX, int sAY, int aSize){
		
	
		parent = p;
	}
	
	void displayAgent(){
		parent.rect(startAgentX,startAgentY,sizeAgent,sizeAgent);
		parent.fill(100,100,100);
	}
	
	
}
