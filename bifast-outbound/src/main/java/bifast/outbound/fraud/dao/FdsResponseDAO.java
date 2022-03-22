package bifast.outbound.fraud.dao;

public class FdsResponseDAO {

	private String TransactionDateTime;
	private String ScoringScore;
	private String Grade;
	private String TriggeredRules;
	private String AssignedUser;
	private String AssignedTeam;
	private String Alert;
	private String RiskLevel;
	private String RuleDecision;
	private String RandomNumber;
	private String ActionsQueued;
	private String MachineLearningScore;
	
	public String getTransactionDateTime() {
		return TransactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		TransactionDateTime = transactionDateTime;
	}
	public String getScoringScore() {
		return ScoringScore;
	}
	public void setScoringScore(String scoringScore) {
		ScoringScore = scoringScore;
	}
	public String getGrade() {
		return Grade;
	}
	public void setGrade(String grade) {
		Grade = grade;
	}
	public String getTriggeredRules() {
		return TriggeredRules;
	}
	public void setTriggeredRules(String triggeredRules) {
		TriggeredRules = triggeredRules;
	}
	public String getAssignedUser() {
		return AssignedUser;
	}
	public void setAssignedUser(String assignedUser) {
		AssignedUser = assignedUser;
	}
	public String getAssignedTeam() {
		return AssignedTeam;
	}
	public void setAssignedTeam(String assignedTeam) {
		AssignedTeam = assignedTeam;
	}
	public String getAlert() {
		return Alert;
	}
	public void setAlert(String alert) {
		Alert = alert;
	}
	public String getRiskLevel() {
		return RiskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		RiskLevel = riskLevel;
	}
	public String getRuleDecision() {
		return RuleDecision;
	}
	public void setRuleDecision(String ruleDecision) {
		RuleDecision = ruleDecision;
	}
	public String getRandomNumber() {
		return RandomNumber;
	}
	public void setRandomNumber(String randomNumber) {
		RandomNumber = randomNumber;
	}
	public String getActionsQueued() {
		return ActionsQueued;
	}
	public void setActionsQueued(String actionsQueued) {
		ActionsQueued = actionsQueued;
	}
	public String getMachineLearningScore() {
		return MachineLearningScore;
	}
	public void setMachineLearningScore(String machineLearningScore) {
		MachineLearningScore = machineLearningScore;
	}
	
	
}
