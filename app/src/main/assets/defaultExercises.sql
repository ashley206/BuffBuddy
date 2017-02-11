INSERT INTO
EXERCISE
(NAME,
    PRIMARY_TARGET_MUSCLE,
    SECONDARY_TARGET_MUSCLE,
    SETS,
    REP1, REP2, REP3, REP4, REP5, REP6,
    WORKOUT_ID)
VALUES
("Barbell Squats","Quadriceps","Glutes",    5, 14, 14, 12, 12, 10, NULL, 1),
("Leg Extension", "Quadriceps", NULL,         6, 15, 35, 30, 25, 20, 15, 2),
("Lunge", "Quadriceps", "Glutes",           5, 10, 10, 10, 10, 10, NULL, 3),
("Leg Press", "Quadriceps", "Hamstrings",   6, 20, 40, 35, 30, 25, 20, 3),
("Leg Press", "Quadriceps", "Hamstrings",   5, 40, 35, 30, 25, 20, NULL, 1),

("Barbell Bench Press", "Pectorals", NULL,    5, 15, 15, 12, 12, 10, NULL, 4),
("Dumbell Incline Press", "Pectorals", NULL,  5, 15, 15 ,12, 12 ,10, NULL, 5),
("Dumbell Flyes", "Pectorals", NULL,          4, 15, 12, 10, 10, NULL, NULL, 5),
("Incline Flyes", "Pectorals", NULL,          4, 15, 12, 10, 10, NULL, NULL, 4),
("Cable Crossover", "Pectorals", NULL,        6, 12 , 12, 10, 10, 8, 8, 4),
("Side Lateral Raise", "Medial Deltoids", "Posterior Deltoids", 4, 10, 10, 10, 10, NULL, NULL,8),
("Shoulder Press", "Medial Deltoids", "Anterior Deltoids",      4, 12, 10, 8, 6, NULL, NULL, 10),
("Arnold Press", "Medial Deltoids", "Anterior Deltoids",        5, 10, 10, 8, 8, 6, NULL, 10),

("Front Raise", "Anterior Deltoids", "Medial Deltoids", 4, 10, 10, 10, 10, NULL, NULL, 10),

("Seated Bent-Over Delt Raise", "Posterior Deltoids", "Medial Deltoids",    4, 15, 12, 10, 10, NULL, NULL, 9),
("Dumbbell Row", "Posterior Deltoids", NULL,                                  4, 12, 10, 8, 8, NULL, NULL, 10),
("Seated Reverse Cable Fly", "Posterior Deltoids", NULL,                      3, 15, 15, 12, NULL, NULL, NULL,9),

("Lat Pull Down", "Back", NULL,                           5, 15, 15, 12, 12, 10, NULL, 8),
("Seated Close-Grip Cable Row", "Back", "Trapezius",    5, 15, 15, 12, 12, 10, NULL, 8),
("Bent-Over Row", "Back", "Trapezius",                  4, 15, 15, 12, 10, NULL, NULL, 9),
("Straight-Arm Pulldown", "Back", NULL,                   5, 15, 15, 12, 12, 10, NULL,9),

("Supermans", "Lower Back", NULL,                 3, 20, 20, 20, NULL, NULL ,NULL, 10),
("Opposite Arm/Leg Raises", "Lower Back", NULL,   3, 20, 20, 20, NULL, NULL, NULL, 10),
("Child Pose", "Lower Back", NULL,                2, 30, 30, NULL, NULL, NULL, NULL, 10),
("Windshield Wipers", "Lower Back", NULL,         3, 20, 20, 20, NULL, NULL, NULL, 10),

("Hammer Curls", "Biceps", NULL,              4, 10, 10, 8, 6, NULL, NULL, 6),
("Rope Curls", "Biceps", NULL,                4, 15, 12, 10, 10, NULL, NULL, 7),
("Wide Grip Barbell Curls", "Biceps", NULL,   5, 12, 12, 10, 10, 8, NULL, 6),

("Tricep Pushdown", "Triceps", NULL,              4, 15, 15, 12, 10, NULL, NULL, 6),
("Tricep Kickbacks", "Triceps", NULL,             4, 12, 12, 10, 8, NULL, NULL, 5),
("Reverse Grip Tricep Pushdown", "Triceps", NULL, 4, 15, 12, 10,10, NULL, NULL, 4),

("Standing Calf Raises", "Calves", NULL,          4, 15, 15, 12, 10, NULL, NULL, 2),
("Calf Press", "Calves", NULL,                    3, 15, 20, 20, NULL, NULL, NULL, 3),

("Upright Row", "Trapezius", "Posterior Deltoids",      4, 15, 12, 10, 10, NULL, NULL, 7),
("Dumbbell Shrug", "Trapezius", "Posterior Deltoids",   3, 12, 12, 12, NULL, NULL, NULL,7),
("Barbell Shrug", "Trapeziuz", NULL,                      3, 10, 10, 10, NULL, NULL, NULL,9),

("Leg Curls", "Hamstrings", NULL,                 4, 20, 16, 12, 10, NULL, NULL, 2),
("Stiff-Leg Deadlift", "Hamstrings", "Glutes",  5, 16, 16, 14, 14, 12, NULL, 1),
("Romanian Deadlift", "Hamstring", "Glutes",    3, 20, 20, 20, NULL, NULL, NULL, 2),

("Bridges", "Glutes", "Hamstrings",     4, 15, 15, 12, 10, NULL, NULL,3),
("Barbell Hip Thrusters", "Glutes", NULL, 4, 12, 12, 10, 8, NULL, NULL, 2),
("Leg Lifts", "Glutes", "Hamstrings",   3, 15, 15, 15, NULL, NULL, NULL, 3),

("Plank", "Abdominals", NULL,         3, 1, 1, 1, NULL, NULL, NULL, 11),
("Leg Raises", "Abdominals", NULL,    3, 20, 20, 20, NULL, NULL, NULL, 12),
("Flutter Kicks", "Abdominals", NULL, 3, 30, 30, 30, NULL, NULL, NULL,12),

("Russian Twists", "Obliques", "Abdominals",    3, 20, 20, 20, NULL, NULL, NULL, 11),
("Heel Touches", "Obliques", NULL,                3, 30, 30, 30, NULL, NULL, NULL, 12),
("Side Plank", "Obliques", "Abdominals",        3, 1, 1, 1, NULL, NULL, NULL, 11);