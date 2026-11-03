# Hospital Emergency Room Simulation

A Java simulation of a busy hospital emergency room that implements the classic **Producer–Consumer problem** using a **Bounded Buffer** and **semaphores**. Patients (producers) arrive and try to enter a limited waiting room, while doctors (consumers) take patients from it, acquire one of the two treatment rooms, and treat them concurrently, with no race conditions and no deadlocks.

## How It Works

- Each patient arrives and tries to enter the waiting room (limited capacity set by the user).
- If the waiting room is full, the patient waits outside until a seat becomes available.
- Idle doctors wait until a patient is available, then are assigned to one patient.
- Before treating, a doctor tries to acquire one of the **2 treatment rooms** within **3 seconds**.
  - If it succeeds, treatment starts and lasts a random amount of time.
  - If it times out, the doctor sends the patient back to the waiting room.
- After treatment, the doctor releases the room, becomes available again, and the patient leaves.

The waiting room and the treatment rooms are **separate shared resources**, each synchronized independently.

## Project Structure

| Class | Responsibility |
|-------|----------------|
| `Hospital` | Reads user input, creates doctors and patients, displays simulation events |
| `Semaphore` | Custom semaphore implementation, including a timed acquire (`tryAcquire(timeout)`) that returns `false` on timeout |
| `Patient` | Arrives, then waits outside or enters the waiting room |
| `Doctor` | Idles, takes a patient, tries to acquire a room, treats, releases, becomes available |
| Shared-resource classes | Bounded-buffer waiting room and treatment room pool with their synchronization logic |

## Synchronization Design

- **Semaphores** guard every shared resource.
- **Bounded buffer** models the waiting room (empty/full slot semaphores plus a mutex).
- **Timed semaphore acquire** models the 3-second treatment room attempt.
- Treatment rooms (count = 2) are managed independently from the waiting room to avoid deadlock.

## Requirements

- Java JDK 11 or later

## Build and Run

```bash
# compile
javac -d out src/*.java

# run
java -cp out Hospital
```

You will be prompted for:

```
Waiting room capacity:
Number of doctors:
Number of patients:
```

## Sample Run

Input:

```
Waiting room capacity: 2
Number of doctors: 3
Number of patients: 5
```

Output (one possible scenario, abridged):

```
P1 arrived.
P1 entered the waiting room.
Doctor 1 is assigned to P1.
Doctor 1 is trying to acquire a treatment room.
Doctor 1 acquired a treatment room.
Doctor 1 begins treating P1.
P5 arrived.
The waiting room is full.
P5 is waiting outside for an available seat.
Doctor 3 could not acquire a treatment room.
Doctor 3 is sending P3 back to the waiting room.
Doctor 1 finished treating P1.
Doctor 1 released the treatment room.
P1 left.
...
All patients have been treated.
Simulation completed.
```

## Team

| Name | GitHub |
|------|--------|
| Mohamed Ibrahim | [@Longlivevetchoo](https://github.com/Longlivevetchoo) |
| Ahmed Mohamed Saed | [@master00x](https://github.com/master00x) |
| Abdelrhman Nazeeh Nagy| [@abdelrahman-nazeeh](https://github.com/abdelrahman-nazeeh) |
| Saif Eldeen Mohamed| [@1saifmohamed](https://github.com/1saifmohamed) |

## Academic Note

This project was completed as coursework. Please follow your institution's academic integrity policy before reusing any part of it.
