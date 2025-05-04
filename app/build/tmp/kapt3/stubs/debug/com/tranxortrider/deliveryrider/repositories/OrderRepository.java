package com.tranxortrider.deliveryrider.repositories;

/**
 * Repository class for all order-related operations with Firestore
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010$\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JF\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JF\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JR\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u00042\n\b\u0002\u0010\u001c\u001a\u0004\u0018\u00010\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JZ\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011Jg\u0010\u001f\u001a\u00020\u000e2_\u0010\u0010\u001a[\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\'\u0012%\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\"\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(#\u0012\u0004\u0012\u00020\u000e0 JN\u0010$\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010&\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010\'\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JF\u0010(\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JU\u0010)\u001a\u00020\u000e2M\u0010\u0010\u001aI\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\u000e0 JR\u0010*\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010,\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011J\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00010!2\u0006\u0010.\u001a\u00020\u00042\u0006\u0010/\u001a\u000200H\u0002JU\u00101\u001a\u00020\u000e2M\u0010\u0010\u001aI\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u000102\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(3\u0012\u0004\u0012\u00020\u000e0 J[\u00104\u001a\u00020\u000e2S\u0010\u0010\u001aO\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u000e0 J[\u00107\u001a\u00020\u000e2S\u0010\u0010\u001aO\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u000e0 Jl\u00108\u001a\u00020\u000e2\n\b\u0002\u00109\u001a\u0004\u0018\u00010:2S\u0010\u0010\u001aO\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u000e0 \u00a2\u0006\u0002\u0010;Jc\u0010<\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042S\u0010\u0010\u001aO\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u000e0 Jo\u0010=\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042_\u0010\u0010\u001a[\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\'\u0012%\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u00020?0>\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0004\u0012\u00020\u000e0 J>\u0010@\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010A\u001a\u00020?2&\u0010\u0010\u001a\"\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0004\u0012\u000205\u0018\u00010!\u0012\u0004\u0012\u00020\u000e0 J\u00a7\u0001\u0010B\u001a\u00020\u000e2\b\b\u0002\u0010A\u001a\u00020?2\b\b\u0002\u0010C\u001a\u00020?2\n\b\u0002\u0010D\u001a\u0004\u0018\u00010\u00042\u007f\u0010\u0010\u001a{\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(F\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(G\u0012\u0004\u0012\u00020\u000e0EJ]\u0010H\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042M\u0010\u0010\u001aI\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0015\u0012\u0013\u0018\u000105\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(I\u0012\u0004\u0012\u00020\u000e0 J\u00a7\u0001\u0010J\u001a\u00020\u000e2\b\b\u0002\u0010A\u001a\u00020?2\b\b\u0002\u0010C\u001a\u00020?2\n\b\u0002\u0010D\u001a\u0004\u0018\u00010\u00042\u007f\u0010\u0010\u001a{\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(6\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(F\u0012\u0015\u0012\u0013\u0018\u00010\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(G\u0012\u0004\u0012\u00020\u000e0EJN\u0010K\u001a\u00020\u000e2\u0006\u0010L\u001a\u00020526\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011H\u0082@\u00a2\u0006\u0002\u0010MJc\u0010N\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042S\u0010\u0010\u001aO\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u000205\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(O\u0012\u0004\u0012\u00020\u000e0 JF\u0010P\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010Q\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010R\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010S\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JF\u0010T\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JF\u0010U\u001a\u00020\u000e2\u0006\u0010V\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011Jm\u0010W\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\u00042\b\b\u0002\u0010/\u001a\u0002002S\u0010\u0010\u001aO\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u001b\u0012\u0019\u0012\u0004\u0012\u00020\u0001\u0018\u00010!\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(X\u0012\u0004\u0012\u00020\u000e0 J\u001c\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00010!2\u0006\u0010.\u001a\u00020\u00042\u0006\u0010/\u001a\u000200Jk\u0010Z\u001a\u00020\u000e2\u0006\u0010[\u001a\u00020:2\u0006\u0010\\\u001a\u00020:2\n\b\u0002\u0010]\u001a\u0004\u0018\u00010^2\n\b\u0002\u0010_\u001a\u0004\u0018\u00010^26\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011\u00a2\u0006\u0002\u0010`JF\u0010a\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JE\u0010b\u001a\u00020\u000e2\n\b\u0002\u00109\u001a\u0004\u0018\u00010:2\b\b\u0002\u0010c\u001a\u00020?2\b\b\u0002\u0010d\u001a\u00020e2\u0018\u0010f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002050!\u0012\u0004\u0012\u00020\u000e0g\u00a2\u0006\u0002\u0010hJ2\u0010i\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u00042\b\b\u0002\u0010d\u001a\u00020e2\u0018\u0010f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002050!\u0012\u0004\u0012\u00020\u000e0gJ$\u0010j\u001a\u00020\u000e2\b\b\u0002\u0010d\u001a\u00020e2\u0012\u0010k\u001a\u000e\u0012\u0004\u0012\u000205\u0012\u0004\u0012\u00020\u000e0gJN\u0010l\u001a\u00020\u000e2\u0006\u0010[\u001a\u00020:2\u0006\u0010\\\u001a\u00020:26\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010m\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010n\u001a\u00020o26\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JP\u0010p\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010n\u001a\u00020o26\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011H\u0002JF\u0010q\u001a\u00020\u000e2\u0006\u0010r\u001a\u00020\u001226\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011JN\u0010s\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010t\u001a\u00020\u000426\u0010\u0010\u001a2\u0012\u0013\u0012\u00110\u0012\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u000e0\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006u"}, d2 = {"Lcom/tranxortrider/deliveryrider/repositories/OrderRepository;", "", "()V", "TAG", "", "db", "Lcom/google/firebase/firestore/FirebaseFirestore;", "firestoreService", "Lcom/tranxortrider/deliveryrider/services/FirestoreService;", "ordersCollection", "Lcom/google/firebase/firestore/CollectionReference;", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "acceptAdminAssignedOrder", "", "orderId", "callback", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "success", "message", "acceptOrder", "addOrderToBatch", "batchId", "adminApproveRider", "riderId", "notes", "adminAssignOrder", "adminNotes", "adminGetPendingRiders", "Lkotlin/Function3;", "", "", "riders", "adminRejectRider", "reason", "adminUnassignOrder", "cancelOrder", "completeBatch", "createBatch", "deliverOrder", "deliveryNotes", "failOrder", "generateMockSearchResults", "query", "filter", "Lcom/tranxortrider/deliveryrider/repositories/SearchResultType;", "getActiveBatch", "Lcom/tranxortrider/deliveryrider/batch$BatchDetails;", "batchDetails", "getAssignedOrders", "Lcom/tranxortrider/deliveryrider/models/Order;", "orders", "getAvailableOrdersForBatch", "getAvailableOrdersNearby", "maxDistance", "", "(Ljava/lang/Double;Lkotlin/jvm/functions/Function3;)V", "getBatchOrders", "getBatchOrdersWithSequence", "Lkotlin/Pair;", "", "getCompletedOrders", "page", "getFailedOrders", "limit", "lastDocumentId", "Lkotlin/Function5;", "hasMore", "lastDocId", "getOrderDetails", "order", "getPendingOrders", "handleOrderAcceptance", "newOrder", "(Lcom/tranxortrider/deliveryrider/models/Order;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "optimizeBatchRoute", "optimizedOrders", "pickupOrder", "rejectAdminAssignedOrder", "rejectOrder", "removeOrderFromBatch", "requestOrderAssignment", "scanPackage", "barcode", "search", "results", "searchOrders", "sendLocationUpdateToAdmin", "latitude", "longitude", "speed", "", "bearing", "(DDLjava/lang/Float;Ljava/lang/Float;Lkotlin/jvm/functions/Function2;)V", "startBatch", "startListeningForAvailableOrders", "maxOrders", "listener", "Lcom/tranxortrider/deliveryrider/services/FirestoreListenerService;", "onUpdate", "Lkotlin/Function1;", "(Ljava/lang/Double;ILcom/tranxortrider/deliveryrider/services/FirestoreListenerService;Lkotlin/jvm/functions/Function1;)V", "startListeningForBatchOrders", "startListeningForOrderAssignments", "onOrderAssigned", "updateLocation", "updateOrderStatus", "newStatus", "Lcom/tranxortrider/deliveryrider/models/OrderStatus;", "updateOrderStatusNormal", "updateRiderOnlineStatus", "isOnline", "verifyDeliveryCode", "code", "app_debug"})
public final class OrderRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore db = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.storage.FirebaseStorage storage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference ordersCollection = null;
    @org.jetbrains.annotations.NotNull()
    private final com.tranxortrider.deliveryrider.services.FirestoreService firestoreService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "OrderRepository";
    
    public OrderRepository() {
        super();
    }
    
    /**
     * Get pending orders
     */
    public final void getPendingOrders(int page, int limit, @org.jetbrains.annotations.Nullable()
    java.lang.String lastDocumentId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function5<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, ? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get assigned orders for a rider
     */
    public final void getAssignedOrders(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> callback) {
    }
    
    /**
     * Get completed orders for a rider with pagination
     */
    public final void getCompletedOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String riderId, int page, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> callback) {
    }
    
    /**
     * Get failed orders for a rider
     */
    public final void getFailedOrders(int page, int limit, @org.jetbrains.annotations.Nullable()
    java.lang.String lastDocumentId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function5<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, ? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get order details
     */
    public final void getOrderDetails(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> callback) {
    }
    
    /**
     * Accept an order
     */
    public final void acceptOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Reject an order
     */
    public final void rejectOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Mark order as picked up
     */
    public final void pickupOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Mark order as delivered
     */
    public final void deliverOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.Nullable()
    java.lang.String deliveryNotes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Mark order as failed
     */
    public final void failOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Cancel an order
     */
    public final void cancelOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Update rider location
     */
    public final void updateLocation(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Update order status
     * Provides an automatic route optimization when changing from PENDING to ACCEPTED
     */
    public final void updateOrderStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.models.OrderStatus newStatus, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Creates or updates a batch with optimized route when accepting an order
     */
    private final java.lang.Object handleOrderAcceptance(com.tranxortrider.deliveryrider.models.Order newOrder, kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Standard order status update without batching
     */
    private final void updateOrderStatusNormal(java.lang.String orderId, com.tranxortrider.deliveryrider.models.OrderStatus newStatus, kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get orders for a batch with their sequence numbers
     */
    public final void getBatchOrdersWithSequence(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<kotlin.Pair<com.tranxortrider.deliveryrider.models.Order, java.lang.Integer>>, kotlin.Unit> callback) {
    }
    
    /**
     * Get orders for a batch
     */
    public final void getBatchOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> callback) {
    }
    
    /**
     * Complete a batch
     */
    public final void completeBatch(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get the currently active batch for a rider
     */
    public final void getActiveBatch(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super com.tranxortrider.deliveryrider.batch.BatchDetails, kotlin.Unit> callback) {
    }
    
    /**
     * Start a batch delivery
     */
    public final void startBatch(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Optimize the route for a batch
     */
    public final void optimizeBatchRoute(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> callback) {
    }
    
    /**
     * Remove an order from a batch
     */
    public final void removeOrderFromBatch(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Get available orders that can be added to a batch
     */
    public final void getAvailableOrdersForBatch(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> callback) {
    }
    
    /**
     * Create a new batch
     */
    public final void createBatch(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Add an order to a batch
     */
    public final void addOrderToBatch(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Scan a package barcode
     */
    public final void scanPackage(@org.jetbrains.annotations.NotNull()
    java.lang.String barcode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Verify delivery code
     */
    public final void verifyDeliveryCode(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String code, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Search for orders, restaurants, customers, etc.
     */
    public final void search(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.repositories.SearchResultType filter, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<? extends java.lang.Object>, kotlin.Unit> callback) {
    }
    
    private final java.util.List<java.lang.Object> generateMockSearchResults(java.lang.String query, com.tranxortrider.deliveryrider.repositories.SearchResultType filter) {
        return null;
    }
    
    /**
     * Get available orders near rider's location
     */
    public final void getAvailableOrdersNearby(@org.jetbrains.annotations.Nullable()
    java.lang.Double maxDistance, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> callback) {
    }
    
    /**
     * Start listening for available orders in real-time
     */
    public final void startListeningForAvailableOrders(@org.jetbrains.annotations.Nullable()
    java.lang.Double maxDistance, int maxOrders, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.services.FirestoreListenerService listener, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Start listening for order assignments in real-time
     */
    public final void startListeningForOrderAssignments(@org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.services.FirestoreListenerService listener, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.tranxortrider.deliveryrider.models.Order, kotlin.Unit> onOrderAssigned) {
    }
    
    /**
     * Request assignment for an order
     */
    public final void requestOrderAssignment(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Update rider's online status
     */
    public final void updateRiderOnlineStatus(boolean isOnline, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Start listening for batch order updates in real-time
     */
    public final void startListeningForBatchOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String batchId, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.services.FirestoreListenerService listener, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.util.List<com.tranxortrider.deliveryrider.models.Order>, kotlin.Unit> onUpdate) {
    }
    
    /**
     * Send real-time location update to admin panel
     */
    public final void sendLocationUpdateToAdmin(double latitude, double longitude, @org.jetbrains.annotations.Nullable()
    java.lang.Float speed, @org.jetbrains.annotations.Nullable()
    java.lang.Float bearing, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Admin method: Assign order to a specific rider
     */
    public final void adminAssignOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String riderId, @org.jetbrains.annotations.Nullable()
    java.lang.String adminNotes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Admin method: Approve a rider application
     */
    public final void adminApproveRider(@org.jetbrains.annotations.NotNull()
    java.lang.String riderId, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Admin method: Reject a rider application
     */
    public final void adminRejectRider(@org.jetbrains.annotations.NotNull()
    java.lang.String riderId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Admin method: Unassign an order from a rider
     */
    public final void adminUnassignOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Admin method: Get pending riders for approval
     */
    public final void adminGetPendingRiders(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super java.lang.Boolean, ? super java.lang.String, ? super java.util.List<? extends java.util.Map<java.lang.String, ? extends java.lang.Object>>, kotlin.Unit> callback) {
    }
    
    /**
     * Rider method: Accept an admin-assigned order
     */
    public final void acceptAdminAssignedOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Rider method: Reject an admin-assigned order
     */
    public final void rejectAdminAssignedOrder(@org.jetbrains.annotations.NotNull()
    java.lang.String orderId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super java.lang.String, kotlin.Unit> callback) {
    }
    
    /**
     * Search orders by query
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Object> searchOrders(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    com.tranxortrider.deliveryrider.repositories.SearchResultType filter) {
        return null;
    }
}