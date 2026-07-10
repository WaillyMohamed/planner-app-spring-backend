📘 Planner Scheduling App
An intelligent scheduling system built with Java + Spring Boot that automatically organizes a user’s day based on different types of tasks, deadlines, pacing rules, daily routines, fixed appointments, and personal availability.

This project is designed to evolve into a full productivity assistant capable of generating optimized daily planners.

🚀 Features
✔ Three Task Types
The system supports multiple task categories, each with unique scheduling behavior:

1. DeadlineTask
Tasks that must be completed before a specific deadline.
Includes:
- Total duration (in minutes)
- Pacing (minutes per session)
- Priority
- Intensity

The scheduler automatically splits these tasks into multiple sessions and distributes them across days before the deadline.

2. DailyTask
Recurring tasks that appear every day.
- Includes:
- Duration
- Priority
- Intensity

Optional preferred time window

The scheduler places these tasks consistently across all planners.

3. ScheduledTask
Fixed appointments with a specific date and time.
Includes:
- Date
- Start time
- End time
- Priority
- Intensity

These tasks cannot be moved and act as hard constraints in the schedule.

📅 Planner System
A Planner represents a single day in the user’s schedule.
Each planner contains a list of ScheduledTaskInstance objects — the actual placed time blocks for that day.

Planners are generated dynamically by the scheduling engine based on:
- User’s tasks
- User’s availability
- Deadlines
- Pacing rules
- Intensity balancing
- Conflict resolution
