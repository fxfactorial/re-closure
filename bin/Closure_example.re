let main = () => {
  let input = "const a = 1 + 2; console.log(a)";
  Printf.sprintf("Input data was:\n%s", input) |> print_endline;
  let optimized = Closure.optimize(Closure.Advanced, input);
  Printf.sprintf("Output is:\n%s", optimized) |> print_endline;
};

main();
