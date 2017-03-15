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
("Leg Press", "Quadriceps", "Hamstrings",   5, 40, 35, 30, 25, 20, NULL, 1),
("Stiff-Leg Deadlift", "Hamstrings", "Glutes",  5, 16, 16, 14, 14, 12, NULL, 1),

("Leg Extension", "Quadriceps", NULL,         6, 15, 35, 30, 25, 20, 15, 2),
("Standing Calf Raises", "Calves", NULL,          4, 15, 15, 12, 10, NULL, NULL, 2),
("Leg Curls", "Hamstrings", NULL,                 4, 20, 16, 12, 10, NULL, NULL, 2),
("Barbell Hip Thrusters", "Glutes", NULL, 4, 12, 12, 10, 8, NULL, NULL, 2),
("Romanian Deadlift", "Hamstring", "Glutes",    3, 20, 20, 20, NULL, NULL, NULL, 2),

("Bridges", "Glutes", "Hamstrings",     4, 15, 15, 12, 10, NULL, NULL,3),
("Leg Lifts", "Glutes", "Hamstrings",   3, 15, 15, 15, NULL, NULL, NULL, 3),
("Calf Press", "Calves", NULL,                    3, 15, 20, 20, NULL, NULL, NULL, 3),
("Lunge", "Quadriceps", "Glutes",           5, 10, 10, 10, 10, 10, NULL, 3),
("Leg Press", "Quadriceps", "Hamstrings",   6, 20, 40, 35, 30, 25, 20, 3),

("Barbell Bench Press", "Pectorals", NULL,    5, 15, 15, 12, 12, 10, NULL, 4),
("Incline Flyes", "Pectorals", NULL,          4, 15, 12, 10, 10, NULL, NULL, 4),
("Cable Crossover", "Pectorals", NULL,        6, 12 , 12, 10, 10, 8, 8, 4),
("Reverse Grip Tricep Pushdown", "Triceps", NULL, 4, 15, 12, 10,10, NULL, NULL, 4),

("Dumbell Incline Press", "Pectorals", NULL,  5, 15, 15 ,12, 12 ,10, NULL, 5),
("Tricep Kickbacks", "Triceps", NULL,             4, 12, 12, 10, 8, NULL, NULL, 5),
("Dumbell Flyes", "Pectorals", NULL,          4, 15, 12, 10, 10, NULL, NULL, 5),

("Hammer Curls", "Biceps", NULL,              4, 10, 10, 8, 6, NULL, NULL, 6),
("Wide Grip Barbell Curls", "Biceps", NULL,   5, 12, 12, 10, 10, 8, NULL, 6),
("Tricep Pushdown", "Triceps", NULL,              4, 15, 15, 12, 10, NULL, NULL, 6),

("Upright Row", "Trapezius", "Posterior Deltoids",      4, 15, 12, 10, 10, NULL, NULL, 7),
("Dumbbell Shrug", "Trapezius", "Posterior Deltoids",   3, 12, 12, 12, NULL, NULL, NULL,7),
("Rope Curls", "Biceps", NULL,                4, 15, 12, 10, 10, NULL, NULL, 7),

("Side Lateral Raise", "Medial Deltoids", "Posterior Deltoids", 4, 10, 10, 10, 10, NULL, NULL,8),
("Lat Pull Down", "Back", NULL,                           5, 15, 15, 12, 12, 10, NULL, 8),
("Seated Close-Grip Cable Row", "Back", "Trapezius",    5, 15, 15, 12, 12, 10, NULL, 8),

("Seated Bent-Over Delt Raise", "Posterior Deltoids", "Medial Deltoids",    4, 15, 12, 10, 10, NULL, NULL, 9),
("Bent-Over Row", "Back", "Trapezius",                  4, 15, 15, 12, 10, NULL, NULL, 9),
("Seated Reverse Cable Fly", "Posterior Deltoids", NULL,                      3, 15, 15, 12, NULL, NULL, NULL,9),
("Barbell Shrug", "Trapeziuz", NULL,                      3, 10, 10, 10, NULL, NULL, NULL,9),
("Straight-Arm Pulldown", "Back", NULL,                   5, 15, 15, 12, 12, 10, NULL,9),

("Shoulder Press", "Medial Deltoids", "Anterior Deltoids",      4, 12, 10, 8, 6, NULL, NULL, 10),
("Arnold Press", "Medial Deltoids", "Anterior Deltoids",        5, 10, 10, 8, 8, 6, NULL, 10),
("Front Raise", "Anterior Deltoids", "Medial Deltoids", 4, 10, 10, 10, 10, NULL, NULL, 10),
("Dumbbell Row", "Posterior Deltoids", NULL, 4, 12, 10, 8, 8, NULL, NULL, 10),

("Russian Twists", "Obliques", "Abdominals",    3, 20, 20, 20, NULL, NULL, NULL, 11),
("Side Plank", "Obliques", "Abdominals",        3, 1, 1, 1, NULL, NULL, NULL, 11),
("Plank", "Abdominals", NULL,         3, 1, 1, 1, NULL, NULL, NULL, 11),

("Leg Raises", "Abdominals", NULL,    3, 20, 20, 20, NULL, NULL, NULL, 12),
("Flutter Kicks", "Abdominals", NULL, 3, 30, 30, 30, NULL, NULL, NULL,12),
("Heel Touches", "Obliques", NULL,                3, 30, 30, 30, NULL, NULL, NULL, 12),

("Supermans", "Lower Back", NULL,                 3, 20, 20, 20, NULL, NULL ,NULL, 13),
("Opposite Arm/Leg Raises", "Lower Back", NULL,   3, 20, 20, 20, NULL, NULL, NULL, 13),
("Child Pose", "Lower Back", NULL,                2, 30, 30, NULL, NULL, NULL, NULL, 13),
("Windshield Wipers", "Lower Back", NULL,         3, 20, 20, 20, NULL, NULL, NULL, 13);