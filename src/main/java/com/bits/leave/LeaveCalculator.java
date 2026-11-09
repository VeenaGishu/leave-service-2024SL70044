package com.bits.leave;

public class LeaveCalculator {

    // Calculates the remaining leave balance
    public int calculateLeaveBalance(int totalLeaves, int leavesTaken) {

        if (totalLeaves < 0 || leavesTaken < 0) {
            throw new IllegalArgumentException("Leave values cannot be negative");
        }

        if (leavesTaken > totalLeaves) {
            throw new IllegalArgumentException("Leaves taken cannot exceed total leaves");
        }

        return totalLeaves - leavesTaken;
    }

    // Calculates the leave encashment amount
    public double calculateLeaveEncashment(
            int encashableDays, double dailySalary) {

        if (encashableDays < 0 || dailySalary < 0) {
            throw new IllegalArgumentException(
                    "Leave days and salary cannot be negative");
        }

        return encashableDays * dailySalary;
    }
}
