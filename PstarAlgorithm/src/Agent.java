import processing.core.PApplet;



public class Agent{
	
	int startAgentX, startAgentY, sizeAgent;
	
	PApplet parent;
	
	Agent(PApplet p, int sAX, int sAY, int aSize){
		
	
		parent = p;
	}
	
	void displayAgent(){
		parent.fill(0,200,0);
		parent.rect(startAgentX,startAgentY,sizeAgent,sizeAgent);
	}
	
	
}
