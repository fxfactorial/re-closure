type optimize_level = Simple | Advanced;

external optimize : optimize_level => string => string = "closure_ml_optimize";
