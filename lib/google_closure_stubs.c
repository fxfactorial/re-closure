/* -*- c++ -*- */
#include <jni.h>

#include <cstring>
#include <iostream>
#include <string>

#include <caml/alloc.h>
#include <caml/fail.h>
#include <caml/memory.h>
#include <caml/mlvalues.h>

extern "C" {

// NO ERROR CHECKING AT ALL, HARDCODED SHIT, DONT JUDGE ME
CAMLprim value closure_ml_optimize(value optimize_level, value js_string) {
  CAMLparam2(optimize_level, js_string);
  JavaVMOption options[2];
  JNIEnv *env = nullptr;
  JavaVM *jvm = nullptr;
  JavaVMInitArgs vm_args;

  options[0].optionString =
      (char *)"-Djava.class.path=.:./:./closure-compiler-v20171203.jar";

  options[1].optionString = (char *)"CallCompiler";

  memset(&vm_args, 0, sizeof(vm_args));

  vm_args.version = JNI_VERSION_1_8;
  vm_args.nOptions = 1;
  vm_args.options = options;

  long status = JNI_CreateJavaVM(&jvm, (void **)&env, &vm_args);

  jclass cls = nullptr;

  std::string js_input_code(String_val(js_string));

  cls = env->FindClass("CallCompiler");

  jmethodID compile_method = env->GetStaticMethodID(
      cls, "compile", "(Ljava/lang/String;)Ljava/lang/String;");
  jstring input_code = env->NewStringUTF(js_input_code.c_str());

  jstring compiled_js =
      (jstring)env->CallStaticObjectMethod(cls, compile_method, input_code);
  CAMLreturn(caml_copy_string(env->GetStringUTFChars(compiled_js, nullptr)));
}
}
