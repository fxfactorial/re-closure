.PHONY: run clean

run:
	@jbuilder build
	@./_build/default/bin/Closure_example.exe

clean:; @jbuilder clean
