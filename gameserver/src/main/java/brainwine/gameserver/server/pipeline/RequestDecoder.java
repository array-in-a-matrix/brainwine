package brainwine.gameserver.server.pipeline;

import java.io.IOException;
import java.util.List;

import org.msgpack.unpacker.Unpacker;

import brainwine.gameserver.msgpack.MessagePackHelper;
import brainwine.gameserver.server.Request;
import brainwine.gameserver.server.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class RequestDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        int id = buf.readByte() & 0xFF;
        int length = buf.readIntLE();
        
        if(length > 1024) {
            throw new IOException("Request exceeds max length of 1024 bytes");
        }
        
        Request request = NetworkRegistry.instantiateRequest(id);
        
        if(request == null) {
            throw new IOException("Client sent invalid request: " + id);
        }
        
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        Unpacker unpacker = MessagePackHelper.createBufferUnpacker(bytes);
        request.unpack(unpacker);
        unpacker.close();
        out.add(request);
    }
}
