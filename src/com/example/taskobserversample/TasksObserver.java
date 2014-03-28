package com.example.taskobserversample;

import java.util.HashMap;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

public class TasksObserver {

	final private boolean IS_ON = true;
	// Daemon running status
	boolean mObserving = false;
	// Tasks queue
	static HashMap<Integer, ObservedTask> mTasksQueue;
	// Starting uniqueID for the tasks
	private static int mCurrentId = 1234;
	// For future use
	Activity mActivity;
	private boolean PRINT_LOG = true;
	/**
	 * Tasks observer daemon will wake up each DAEMON_SELF_AWAKE_DELAY
	 * miliseconds
	 */
	private int DAEMON_SELF_AWAKE_DELAY = 3000;

	/** Constructor */
	@SuppressLint("UseSparseArrays")
	public TasksObserver(Activity activity) {
		super();
		this.mActivity = activity;
		mTasksQueue = new HashMap<Integer, TasksObserver.ObservedTask>();
	}
	/**Create observer with parameters*/
	public TasksObserver(Activity mActivity, boolean print_log,
			int self_awake_delay) {
		super();
		this.mActivity = mActivity;
		PRINT_LOG = print_log;
		DAEMON_SELF_AWAKE_DELAY = self_awake_delay;
	}
	
	/**In case this constructor is used, no actions on mActivity can be performed*/
	public TasksObserver(boolean print_log, int self_awake_delay) {
		super();
		PRINT_LOG = print_log;
		DAEMON_SELF_AWAKE_DELAY = self_awake_delay;
	}
	

	/** Tasks types */
	public static enum TASK_TYPE {
		ASYNC_TASK_1, ASYNC_TASK_2, ASYNC_TASK_3, ASYNC_TASK_4
	};

	//Tasks severity Definitions
	public static enum Severity {
		INFO, WARNING, ERROR
	};

	/** Subscribe the observer for task that has started */
	public int subscribe(ObservedTask task) {
		if (IS_ON)// we only perform "Subscribe" if the feature defined as ON
		{
			// change id for the next task so it will keep being unique
			mTasksQueue.put(task.mTaskId, task); // add task to queue
			if (!mObserving) // if daemon not running - wake him up
				checkTasks();
			mObserving = true;
			if (PRINT_LOG)
				Log.i("Observer", "Subscribed to task id: " + task.mTaskId
						+ " Type " + task.mTaskType.toString());
			return task.mTaskId;
		} else {// The returning zero keeps us from performing anything wh/en we
				// attempt to perform "unsubscribe"
			return 0;
		}
	}

	/** Unsubscribe the observer from task that has been finished */
	public void unsubscribe(int taskID) {

		if (taskID != 0)// we will not try to unsubscribe from task if the
						// passed id is null
		{
			ObservedTask tempTask = mTasksQueue.get(new Integer(taskID));
			if (tempTask != null) {// if for some reason the task was already
									// unsubscribed we will not attempt to
									// unsubscribe it again
				tempTask.notifyFinished();
				if (PRINT_LOG) {
					Log.i("Observer",
							"Unsubscribe called from task id: "
									+ tempTask.mTaskId + " Type "
									+ tempTask.mTaskType.toString());
				}
			}

		}

	}

	/** Create new task from outside of the class */
	public ObservedTask createNewTask(TASK_TYPE mTaskType, int mParentTaskId,
			double timelimit)

	{
		ObservedTask temp = new ObservedTask(mTaskType, mParentTaskId,
				timelimit);
		return temp;

	}

	/** Check and manage running tasks */
	private void checkTasks() {
		if (PRINT_LOG)
			Log.i("Observer", "Daemon Started");

		new Thread(new Runnable() {
			public void run() {
				while (mTasksQueue.size() > 0)// Daemon will be running as long
												// as we have some tasks in
												// queue
				{
					long currentTime = System.currentTimeMillis();
					for (Integer iterator : mTasksQueue.keySet()) {
						ObservedTask tempTask = mTasksQueue.get(iterator);
						if ((currentTime - tempTask.defined_time_Off > tempTask.startTime)
								&& !tempTask.isFinished)// Task is being late
														// itself or its
														// children are late
						{
							if (!tempTask.isNotified) {// alert has not been
														// shown for this task
														// yet
								if (PRINT_LOG)
									Log.i("Observer", "Keep waiting task: "
											+ tempTask.mTaskId + " type: "
											+ tempTask.mTaskType.toString());

								switch (tempTask.mTaskType) {
								case ASYNC_TASK_1: {

									if (tempTask.mSeverity == Severity.INFO)// Define
																			// Severity
																			// level
																			// required
																			// for
																			// specified
																			// action
																			// to
																			// be
																			// action to be taken
									{
										// TODO: Actions to perform when task
										// takes longer than it supposed to
										if (PRINT_LOG)
											Log.i("Observer",
													"Action taken. Task: "
															+ tempTask.mTaskId
															+ " type: "
															+ tempTask.mTaskType
																	.toString());
										tempTask.isNotified = true;
									} else
										// Raise severity
										tempTask.mSeverity = Severity.values()[tempTask.mSeverity
												.ordinal() + 1];
								}
									break;
								case ASYNC_TASK_2: {
									if (tempTask.mSeverity == Severity.INFO)// Define
																			// Severity
																			// level
																			// required
																			// for
																			// action to be taken
									{
										// TODO: Actions to perform when task
										// takes longer than it supposed to
										if (PRINT_LOG)
											Log.i("Observer",
													"Action taken. Task: "
															+ tempTask.mTaskId
															+ " type: "
															+ tempTask.mTaskType
																	.toString());
										tempTask.isNotified = true;
									} else
										// Raise severity
										tempTask.mSeverity = Severity.values()[tempTask.mSeverity
												.ordinal() + 1];
								}
									break;
								case ASYNC_TASK_3: {
									if (tempTask.mSeverity == Severity.INFO)// Define
																			// Severity
																			// level
																			// required
																			// for
																			// action to be taken
									{
										// TODO: Actions to perform when task
										// takes longer than it supposed to
										if (PRINT_LOG)
											Log.i("Observer",
													"Action taken. Task: "
															+ tempTask.mTaskId
															+ " type: "
															+ tempTask.mTaskType
																	.toString());
										tempTask.isNotified = true;
									} else
										// Raise severity
										tempTask.mSeverity = Severity.values()[tempTask.mSeverity
												.ordinal() + 1];
								}
									break;
								case ASYNC_TASK_4: {
									if (tempTask.mSeverity == Severity.INFO)// Define
																			// Severity
																			// level
																			// required
																			// for
																			// action to be taken
									{
										// TODO: Actions to perform when task
										// takes longer than it supposed to
										if (PRINT_LOG)
											Log.i("Observer",
													"Action taken. Task: "
															+ tempTask.mTaskId
															+ " type: "
															+ tempTask.mTaskType
																	.toString());
										tempTask.isNotified = true;
									} else
										// Raise severity
										tempTask.mSeverity = Severity.values()[tempTask.mSeverity
												.ordinal() + 1];
								}
									break;

								default:
									break;
								}

							} else if (PRINT_LOG)
								Log.i("Observer", "Keep waiting task: "
										+ tempTask.mTaskId + " type: "
										+ tempTask.mTaskType.toString());
						}
					}
					// Go to sleep till the next check
					try {
						Thread.sleep(DAEMON_SELF_AWAKE_DELAY);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (PRINT_LOG)
					Log.i("Observer", "Daemon Finished");
				mObserving = false;
			}
		}).start();

	}

	public class ObservedTask {
		TASK_TYPE mTaskType = null;
		private int mTaskId = -1;
		private int mParentTaskId = -1;
		private int childrenCount = -1;
		private boolean isFinished = false; // Flag for finished tasks that
											// their children have not finish
											// running yet
		private boolean isNotified = false; // notification regarding this task
											// has already been presented to the
											// user
		private double startTime = 0;
		private double defined_time_Off = 0; // Time limit for the task to
												// finish running
		private Severity mSeverity = Severity.INFO;

		public ObservedTask(TASK_TYPE mTaskType, int mParentTaskId,
				double timelimit) {

			super();
			this.mTaskType = mTaskType;
			this.mTaskId = mCurrentId;
			mCurrentId++;
			this.startTime = System.currentTimeMillis();
			this.mParentTaskId = mParentTaskId;
			if (mParentTaskId != -1) {
				mTasksQueue.get(mParentTaskId).childrenCount++;
			}
			
			// If no expected task work time were entered, we use defaults
			// defined here
			if (timelimit == 0)
				switch (mTaskType) {

				case ASYNC_TASK_1:
					defined_time_Off = 3000;
					break;
				case ASYNC_TASK_2:
					defined_time_Off = 3000;
					break;

				case ASYNC_TASK_3:
					defined_time_Off = 4000;
					break;
				case ASYNC_TASK_4:
					defined_time_Off = 4000;
					break;
				default:
					break;
				}

		}

		/**
		 * Notify task was finished itself Task will not finish till all of its
		 * children are finished running
		 */
		public void notifyFinished() {
			if (this.childrenCount == -1) // Task never had any child tasks
			{
				if (mParentTaskId != -1)
					notifyParent();
				TasksObserver.mTasksQueue.remove(mTaskId);

			} else // This is a Parent task
			{
				if (childrenCount == 0) // Task had child tasks but all of them
										// are finished
				{
					TasksObserver.mTasksQueue.remove(mTaskId);
				} else // Task still has some child tasks that have to finish
						// before it is removed
				{
					this.isFinished = true; // Other ways we mark it as finished
											// and keep on waiting for the
											// children to finish as well
					Log.i("Observer", "Task was not removed");
				}
			}
		}

		/** Notify parent task that one of his children has finished running */
		private void notifyParent() {
			ObservedTask tempParent = TasksObserver.mTasksQueue
					.get(new Integer(this.mParentTaskId));

			if (tempParent.childrenCount <= 1 && tempParent.isFinished) // Removing
																		// last
																		// child
																		// task
			{
				TasksObserver.mTasksQueue
						.remove(new Integer(tempParent.mTaskId));
			} else
				// removing NOT last task
				tempParent.childrenCount--;

		}

	}

}
