package com.tranxortrider.deliveryrider.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00b2\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010 \n\u0002\u0010$\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u001c\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0014J,\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ0\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u001c\u001a\u00020\u00042\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001aJ8\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J.\u0010#\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010%0$0\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\'J,\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b*\u0010\u001aJ,\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b,\u0010\u001aJ8\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00042\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b/\u0010\"J8\u00100\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u00101\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00042\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b2\u0010\"J*\u00103\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\f\u00104\u001a\b\u0012\u0004\u0012\u0002050$H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b6\u00107J(\u00108\u001a\u0002092\u0006\u0010:\u001a\u0002092\u0006\u0010;\u001a\u0002092\u0006\u0010<\u001a\u0002092\u0006\u0010=\u001a\u000209H\u0002J,\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b?\u0010\u001aJ\u0016\u0010@\u001a\u00020A2\u0006\u0010\u001c\u001a\u00020\u0004H\u0082@\u00a2\u0006\u0002\u0010\u0014J(\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020E2\u0006\u0010G\u001a\u0002092\u0006\u0010H\u001a\u000209H\u0002J$\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0018\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bJ\u0010\u0014J@\u0010K\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010%2\u0006\u0010.\u001a\u00020\u00042\u0006\u0010L\u001a\u00020\u00042\u0006\u0010M\u001a\u00020\u00042\u0012\u0010N\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010%H\u0002J\u001e\u0010O\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bP\u0010\'J$\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\u00102\u0006\u0010S\u001a\u00020RH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bT\u0010UJ0\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\n\b\u0002\u0010W\u001a\u0004\u0018\u00010\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bX\u0010\u001aJ\u0010\u0010Y\u001a\u0004\u0018\u00010E2\u0006\u0010Z\u001a\u00020[J,\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b]\u0010\u001aJ,\u0010^\u001a\b\u0012\u0004\u0012\u00020E0$2\f\u0010_\u001a\b\u0012\u0004\u0012\u00020E0$2\u0006\u0010G\u001a\u0002092\u0006\u0010H\u001a\u000209H\u0002J\u001e\u0010`\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010a0\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bb\u0010\'J\"\u0010c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$0\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bd\u0010\'J.\u0010e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$0\u00102\n\b\u0002\u0010f\u001a\u0004\u0018\u000109H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bg\u0010hJ*\u0010i\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$0\u00102\u0006\u0010\u0018\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bj\u0010\u0014J6\u0010k\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020C0l0$0\u00102\u0006\u0010\u0018\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bm\u0010\u0014JF\u0010n\u001a\u001c\u0012\u0018\u0012\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$\u0012\u0006\u0012\u0004\u0018\u00010[0l0\u00102\b\b\u0002\u0010o\u001a\u00020p2\n\b\u0002\u0010q\u001a\u0004\u0018\u00010[H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\br\u0010sJ*\u0010t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020u0$0\u00102\u0006\u0010\u001c\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bv\u0010\u0014J\u0010\u0010w\u001a\u00020\u00042\u0006\u0010x\u001a\u00020\u0004H\u0002J,\u0010y\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020z0$0\u00102\b\b\u0002\u0010o\u001a\u00020CH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b{\u0010|JF\u0010}\u001a\u001c\u0012\u0018\u0012\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$\u0012\u0006\u0012\u0004\u0018\u00010[0l0\u00102\b\b\u0002\u0010o\u001a\u00020p2\n\b\u0002\u0010q\u001a\u0004\u0018\u00010[H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b~\u0010sJ\'\u0010\u007f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010E0\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0080\u0001\u0010\u0014J\u001e\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u0002090\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0082\u0001\u0010\'J$\u0010\u0083\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$0\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0084\u0001\u0010\'J\u001e\u0010\u0085\u0001\u001a\b\u0012\u0004\u0012\u0002090\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0086\u0001\u0010\'J\u001e\u0010\u0087\u0001\u001a\b\u0012\u0004\u0012\u0002090\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0088\u0001\u0010\'J \u0010\u0089\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010R0\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u008a\u0001\u0010\'J\u001f\u0010\u0089\u0001\u001a\u00020A2\u0016\u0010\u008b\u0001\u001a\u0011\u0012\u0006\u0012\u0004\u0018\u00010R\u0012\u0004\u0012\u00020A0\u008c\u0001J)\u0010\u0089\u0001\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010R0\u00102\u0007\u0010\u008d\u0001\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u008e\u0001\u0010\u0014J(\u0010\u0089\u0001\u001a\u00020A2\u0007\u0010\u008d\u0001\u001a\u00020\u00042\u0016\u0010\u008b\u0001\u001a\u0011\u0012\u0006\u0012\u0004\u0018\u00010R\u0012\u0004\u0012\u00020A0\u008c\u0001J*\u0010\u008f\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u0002090%0\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0090\u0001\u0010\'J\u001f\u0010\u0091\u0001\u001a\u00020A2\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\u0082@\u00a2\u0006\u0002\u0010\u001aJ,\u0010\u0092\u0001\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020E0$0\u00102\u0006\u0010\u0018\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0093\u0001\u0010\u0014J/\u0010\u0094\u0001\u001a\b\u0012\u0004\u0012\u00020E0$2\f\u0010_\u001a\b\u0012\u0004\u0012\u00020E0$2\u0007\u0010\u0095\u0001\u001a\u0002092\u0007\u0010\u0096\u0001\u001a\u000209H\u0002J&\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0098\u0001\u0010\u0014J\u000f\u0010\u0099\u0001\u001a\u00020AH\u0086@\u00a2\u0006\u0002\u0010\'J.\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u009b\u0001\u0010\u001aJ.\u0010\u009c\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010)\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u009d\u0001\u0010\u001aJ7\u0010\u009e\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00042\u0007\u0010\u009f\u0001\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u00a0\u0001\u0010\"J7\u0010\u00a1\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u00101\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u00042\u0007\u0010\u009f\u0001\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u00a2\u0001\u0010\"J&\u0010\u00a3\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u00a4\u0001\u0010\u0014JM\u0010\u00a5\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0007\u0010\u00a6\u0001\u001a\u0002092\u0007\u0010\u00a7\u0001\u001a\u0002092\f\b\u0002\u0010\u00a8\u0001\u001a\u0005\u0018\u00010\u00a9\u00012\f\b\u0002\u0010\u00aa\u0001\u001a\u0005\u0018\u00010\u00a9\u0001H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00ab\u0001\u0010\u00ac\u0001J&\u0010\u00ad\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0018\u001a\u00020\u0004H\u0082@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u00ae\u0001\u0010\u0014J\'\u0010\u00af\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0007\u0010\u00b0\u0001\u001a\u00020\u0004H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u00b1\u0001\u0010\u0014J4\u0010\u00b2\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0013\u0010\u00b3\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010%H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00b4\u0001\u0010\u00b5\u0001J(\u0010\u00b6\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0007\u0010\u00b7\u0001\u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00b8\u0001\u0010\u00b9\u0001J&\u0010\u00ba\u0001\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010S\u001a\u00020RH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u00bb\u0001\u0010UJ+\u0010\u00ba\u0001\u001a\u00020A2\u0006\u0010S\u001a\u00020R2\u001a\u0010\u008b\u0001\u001a\u0015\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020A0\u00bc\u0001J5\u0010\u00bd\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010%\"\u0005\b\u0000\u0010\u00be\u0001\"\u0005\b\u0001\u0010\u00bf\u0001*\u0010\u0012\u0005\u0012\u0003H\u00be\u0001\u0012\u0005\u0012\u0003H\u00bf\u00010%H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u00c0\u0001"}, d2 = {"Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "", "()V", "TAG", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "batchesCollection", "Lcom/google/firebase/firestore/CollectionReference;", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "earningsCollection", "ordersCollection", "ridersCollection", "usersCollection", "acceptAdminAssignedOrder", "Lkotlin/Result;", "", "orderId", "acceptAdminAssignedOrder-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acceptOrder", "acceptOrder-gIAlu-s", "addOrderToBatch", "batchId", "addOrderToBatch-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "adminApproveRider", "riderId", "notes", "adminApproveRider-0E7RQCE", "adminAssignOrderToRider", "adminNotes", "adminAssignOrderToRider-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "adminGetPendingRiders", "", "", "adminGetPendingRiders-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "adminRejectRider", "reason", "adminRejectRider-0E7RQCE", "adminUnassignOrder", "adminUnassignOrder-0E7RQCE", "approveRiderApplication", "adminId", "approveRiderApplication-BWLJW6A", "approveRiderDocument", "documentId", "approveRiderDocument-BWLJW6A", "bulkUpdateRiderLocations", "locationDataList", "Lcom/tranxortrider/deliveryrider/utils/SharedPreferencesManager$LocationData;", "bulkUpdateRiderLocations-gIAlu-s", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "calculateHaversineDistance", "", "lat1", "lon1", "lat2", "lon2", "cancelOrder", "cancelOrder-0E7RQCE", "checkAndUpdateRiderVerificationStatus", "", "compareOrdersByDistance", "", "order1", "Lcom/tranxortrider/deliveryrider/models/Order;", "order2", "riderLat", "riderLng", "completeBatch", "completeBatch-gIAlu-s", "createAdminLogData", "action", "targetId", "details", "createBatch", "createBatch-IoAF18A", "createUserProfile", "Lcom/tranxortrider/deliveryrider/models/User;", "user", "createUserProfile-gIAlu-s", "(Lcom/tranxortrider/deliveryrider/models/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deliverOrder", "deliveryNotes", "deliverOrder-0E7RQCE", "documentToOrder", "doc", "Lcom/google/firebase/firestore/DocumentSnapshot;", "failOrder", "failOrder-0E7RQCE", "filterAndSortOrdersByDistance", "orders", "getActiveBatch", "Lcom/tranxortrider/deliveryrider/batch$BatchDetails;", "getActiveBatch-IoAF18A", "getAssignedOrders", "getAssignedOrders-IoAF18A", "getAvailableOrdersNearby", "maxDistance", "getAvailableOrdersNearby-gIAlu-s", "(Ljava/lang/Double;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBatchOrders", "getBatchOrders-gIAlu-s", "getBatchOrdersWithSequence", "Lkotlin/Pair;", "getBatchOrdersWithSequence-gIAlu-s", "getCompletedOrders", "limit", "", "lastVisible", "getCompletedOrders-0E7RQCE", "(JLcom/google/firebase/firestore/DocumentSnapshot;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeliveryHistory", "Lcom/tranxortrider/deliveryrider/models/DeliveryHistoryItem;", "getDeliveryHistory-gIAlu-s", "getDocumentFieldName", "documentType", "getEarningsHistory", "Lcom/tranxortrider/deliveryrider/models/Earning;", "getEarningsHistory-gIAlu-s", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFailedOrders", "getFailedOrders-0E7RQCE", "getOrderById", "getOrderById-gIAlu-s", "getPendingEarnings", "getPendingEarnings-IoAF18A", "getPendingOrders", "getPendingOrders-IoAF18A", "getTodayEarnings", "getTodayEarnings-IoAF18A", "getTotalEarnings", "getTotalEarnings-IoAF18A", "getUserProfile", "getUserProfile-IoAF18A", "callback", "Lkotlin/Function1;", "userId", "getUserProfile-gIAlu-s", "getWeeklyEarnings", "getWeeklyEarnings-IoAF18A", "notifyRiderAboutAssignment", "optimizeBatchRoute", "optimizeBatchRoute-gIAlu-s", "optimizeWithNearestNeighbor", "startLat", "startLng", "pickupOrder", "pickupOrder-gIAlu-s", "refreshPendingOrders", "rejectAdminAssignedOrder", "rejectAdminAssignedOrder-0E7RQCE", "rejectOrder", "rejectOrder-0E7RQCE", "rejectRiderApplication", "rejectionReason", "rejectRiderApplication-BWLJW6A", "rejectRiderDocument", "rejectRiderDocument-BWLJW6A", "requestOrderAssignment", "requestOrderAssignment-gIAlu-s", "sendLocationUpdateToAdmin", "latitude", "longitude", "speed", "", "bearing", "sendLocationUpdateToAdmin-yxL6bBk", "(DDLjava/lang/Float;Ljava/lang/Float;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateBatchEstimates", "updateBatchEstimates-gIAlu-s", "updateFcmToken", "token", "updateFcmToken-gIAlu-s", "updateRiderLocation", "locationData", "updateRiderLocation-gIAlu-s", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRiderOnlineStatus", "isOnline", "updateRiderOnlineStatus-gIAlu-s", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateUserProfile", "updateUserProfile-gIAlu-s", "Lkotlin/Function2;", "toFirestoreMap", "K", "V", "app_debug"})
public final class FirestoreService {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "FirestoreService";
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference usersCollection = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference ordersCollection = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference ridersCollection = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference batchesCollection = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference earningsCollection = null;
    
    public FirestoreService() {
        super();
    }
    
    /**
     * Helper method to convert Firestore document to Order object
     */
    @org.jetbrains.annotations.Nullable()
    public final com.tranxortrider.deliveryrider.models.Order documentToOrder(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.DocumentSnapshot doc) {
        return null;
    }
    
    /**
     * Helper method to cast maps to the expected type
     */
    private final <K extends java.lang.Object, V extends java.lang.Object>java.util.Map<java.lang.String, java.lang.Object> toFirestoreMap(java.util.Map<K, ? extends V> $this$toFirestoreMap) {
        return null;
    }
    
    /**
     * Get user profile for the current user (non-suspend version with callback)
     */
    public final void getUserProfile(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.User, kotlin.Unit> callback) {
    }
    
    /**
     * Get user profile for a specific user ID (non-suspend version with callback)
     */
    public final void getUserProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.User, kotlin.Unit> callback) {
    }
    
    /**
     * Update user profile with a User object (non-suspend version with callback)
     */
    public final void updateUserProfile(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.User user, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Optimize route using nearest neighbor algorithm
     * This is a more sophisticated version that considers both pickup and delivery locations
     */
    private final java.util.List<com.tranxortrider.deliveryrider.models.Order> optimizeWithNearestNeighbor(java.util.List<com.tranxortrider.deliveryrider.models.Order> orders, double startLat, double startLng) {
        return null;
    }
    
    /**
     * Calculate distance between two points using Haversine formula
     * This gives a more accurate distance calculation over Earth's surface
     */
    private final double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshPendingOrders(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Notifies a rider about an order assignment
     */
    private final java.lang.Object notifyRiderAboutAssignment(java.lang.String riderId, java.lang.String orderId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Filter orders by distance and sort them
     */
    private final java.util.List<com.tranxortrider.deliveryrider.models.Order> filterAndSortOrdersByDistance(java.util.List<com.tranxortrider.deliveryrider.models.Order> orders, double riderLat, double riderLng) {
        return null;
    }
    
    /**
     * Compare two orders by distance
     */
    private final int compareOrdersByDistance(com.tranxortrider.deliveryrider.models.Order order1, com.tranxortrider.deliveryrider.models.Order order2, double riderLat, double riderLng) {
        return 0;
    }
    
    /**
     * Helper method to convert a HashMap to Map<String, Any> to avoid Java type mismatch errors
     */
    private final java.util.Map<java.lang.String, java.lang.Object> createAdminLogData(java.lang.String adminId, java.lang.String action, java.lang.String targetId, java.util.Map<java.lang.String, ? extends java.lang.Object> details) {
        return null;
    }
    
    /**
     * Check if all required documents are approved and update rider verification status if needed
     */
    private final java.lang.Object checkAndUpdateRiderVerificationStatus(java.lang.String riderId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get the field name for a document type in the rider's documents map
     */
    private final java.lang.String getDocumentFieldName(java.lang.String documentType) {
        return null;
    }
}