.PHONY: run

run:
	@jbuilder build
	@./_build/default/bin/Closure_example.exe
