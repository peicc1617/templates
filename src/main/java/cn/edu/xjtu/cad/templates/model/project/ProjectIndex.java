package cn.edu.xjtu.cad.templates.model.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectIndex {
    long projectID;

    /**
     * 经费
     */
    private double funds;

    /**
     * 客户
     */
    private int customerN;
    /**
     * 成员数量，
     */
    private int memberN;


    /**
     * 会议数
     */
    private int meetingN;
    /**
     * 工作节点数量，
     */
    private int nodeN;

    /**
     * 培训数
     */
    private int trainN;
    /**
     * 收益
     */
    private double income;
    /**
     * 满意度
     */
    private double satisfaction;

    /**
     * 完工率，
     */
    private double completionRate;

    /**
     * 参与指数
     */
    private double participationIndex;

    /**
     * 熟练度
     */
    private double proficiency;
    /**
     * 案例数
     */
    private int caseN;
    /**
     * 论文数
     */
    private int paperN;
    /**
     * 专利数
     */
    private int patentN;
    /**
     * 提案数
     */
    private int proposalN;


    /**
     * 经费
     */
    private double fundsNorm;

    /**
     * 客户
     */
    private double customerNNorm;
    /**
     * 成员数量，
     */
    private double memberNNorm;


    /**
     * 会议数
     */
    private double meetingNNorm;
    /**
     * 工作节点数量，
     */
    private double nodeNNorm;

    /**
     * 培训数
     */
    private double trainNNorm;
    /**
     * 收益
     */
    private double incomeNorm;
    /**
     * 满意度
     */
    private double satisfactionNorm;

    /**
     * 完工率，
     */
    private double completionRateNorm;

    /**
     * 参与指数
     */
    private double participationIndexNorm;

    /**
     * 熟练度
     */
    private double proficiencyNorm;
    /**
     * 案例数
     */
    private double caseNNorm;
    /**
     * 论文数
     */
    private double paperNNorm;
    /**
     * 专利数
     */
    private double patentNNorm;
    /**
     * 提案数
     */
    private double proposalNNorm;

    private double dea;
    /**
     *
     */
    private List<Double>  normalizeIndex;

    public ProjectIndex() {
    }



    public long getProjectID() {
        return projectID;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public int getCustomerN() {
        return customerN;
    }

    public void setCustomerN(int customerN) {
        this.customerN = customerN;
    }

    public int getMemberN() {
        return memberN;
    }

    public void setMemberN(int memberN) {
        this.memberN = memberN;
    }

    public int getMeetingN() {
        return meetingN;
    }

    public void setMeetingN(int meetingN) {
        this.meetingN = meetingN;
    }

    public int getNodeN() {
        return nodeN;
    }

    public void setNodeN(int nodeN) {
        this.nodeN = nodeN;
    }

    public int getTrainN() {
        return trainN;
    }

    public void setTrainN(int trainN) {
        this.trainN = trainN;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(double satisfaction) {
        this.satisfaction = satisfaction;
    }

    public double getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(double completionRate) {
        this.completionRate = completionRate;
    }

    public double getParticipationIndex() {
        return participationIndex;
    }

    public void setParticipationIndex(double participationIndex) {
        this.participationIndex = participationIndex;
    }

    public double getProficiency() {
        return proficiency;
    }

    public void setProficiency(double proficiency) {
        this.proficiency = proficiency;
    }

    public int getCaseN() {
        return caseN;
    }

    public void setCaseN(int caseN) {
        this.caseN = caseN;
    }

    public int getPaperN() {
        return paperN;
    }

    public void setPaperN(int paperN) {
        this.paperN = paperN;
    }

    public int getPatentN() {
        return patentN;
    }

    public void setPatentN(int patentN) {
        this.patentN = patentN;
    }

    public int getProposalN() {
        return proposalN;
    }

    public void setProposalN(int proposalN) {
        this.proposalN = proposalN;
    }

    public Map<String,Double> getIndexMap(){
        return new HashMap<String,Double>(){{
            putAll(getInputIndexMap());
            putAll(getOutputIndexMap());
        }};
    }

    public Map<String,Double> getInputIndexMap(){
        return new HashMap<String,Double>(){{
            put("funds", funds);
            put("memberN", (double)memberN);
            put("meetingN",(double)meetingN);
            put("customerN",(double)customerN);
            put("nodeN",(double)nodeN);
            put("trainN",(double)trainN);
        }};
    }
    public Map<String,Double> getOutputIndexMap(){
        return new HashMap<String,Double>(){{
            put("income",income);
            put("satisfaction",satisfaction);
            put("completionRate",completionRate);
            put("participationIndex",participationIndex);
            put("proficiency",proficiency);
            put("caseN",(double)caseN);
            put("paperN",(double)paperN);
            put("patentN",(double)patentN);
            put("proposalN",(double)proposalN);
        }};
    }

    public double getFundsNorm() {
        return fundsNorm;
    }

    public void setFundsNorm(double fundsNorm) {
        this.fundsNorm = fundsNorm;
    }

    public double getCustomerNNorm() {
        return customerNNorm;
    }

    public void setCustomerNNorm(double customerNNorm) {
        this.customerNNorm = customerNNorm;
    }

    public double getMemberNNorm() {
        return memberNNorm;
    }

    public void setMemberNNorm(double memberNNorm) {
        this.memberNNorm = memberNNorm;
    }

    public double getMeetingNNorm() {
        return meetingNNorm;
    }

    public void setMeetingNNorm(double meetingNNorm) {
        this.meetingNNorm = meetingNNorm;
    }

    public double getNodeNNorm() {
        return nodeNNorm;
    }

    public void setNodeNNorm(double nodeNNorm) {
        this.nodeNNorm = nodeNNorm;
    }

    public double getTrainNNorm() {
        return trainNNorm;
    }

    public void setTrainNNorm(double trainNNorm) {
        this.trainNNorm = trainNNorm;
    }

    public double getIncomeNorm() {
        return incomeNorm;
    }

    public void setIncomeNorm(double incomeNorm) {
        this.incomeNorm = incomeNorm;
    }

    public double getSatisfactionNorm() {
        return satisfactionNorm;
    }

    public void setSatisfactionNorm(double satisfactionNorm) {
        this.satisfactionNorm = satisfactionNorm;
    }

    public double getCompletionRateNorm() {
        return completionRateNorm;
    }

    public void setCompletionRateNorm(double completionRateNorm) {
        this.completionRateNorm = completionRateNorm;
    }

    public double getParticipationIndexNorm() {
        return participationIndexNorm;
    }

    public void setParticipationIndexNorm(double participationIndexNorm) {
        this.participationIndexNorm = participationIndexNorm;
    }

    public double getProficiencyNorm() {
        return proficiencyNorm;
    }

    public void setProficiencyNorm(double proficiencyNorm) {
        this.proficiencyNorm = proficiencyNorm;
    }

    public double getCaseNNorm() {
        return caseNNorm;
    }

    public void setCaseNNorm(double caseNNorm) {
        this.caseNNorm = caseNNorm;
    }

    public double getPaperNNorm() {
        return paperNNorm;
    }

    public void setPaperNNorm(double paperNNorm) {
        this.paperNNorm = paperNNorm;
    }

    public double getPatentNNorm() {
        return patentNNorm;
    }

    public void setPatentNNorm(double patentNNorm) {
        this.patentNNorm = patentNNorm;
    }

    public double getProposalNNorm() {
        return proposalNNorm;
    }

    public void setProposalNNorm(double proposalNNorm) {
        this.proposalNNorm = proposalNNorm;
    }

    public double getDea() {
        return dea;
    }

    public void setDea(double dea) {
        this.dea = dea;
    }
}
