package com.github.jx4e.minecode.rendering;

import com.github.jx4e.minecode.rendering.theme.BoxColorScheme;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jake (github.com/jx4e)
 * @since 11/06/2022
 **/

public class Renderer extends DrawableHelper {
    /*
     * Define our rendering objects
     */
    public final Tessellator tessellator = Tessellator.getInstance();
    public final BufferBuilder bufferBuilder = tessellator.getBuffer();


    /*
     * Rendering QUADS
     */
    /**
     * Renders a quad at the specified location
     * @param matrices - the current matrix stack
     * @param v1 - top left corner of the quad
     * @param v2 - bottom right corner of the quad
     * @param color - color to render the quad
     */
    public void box(MatrixStack matrices, Vec2f v1, Vec2f v2, Color color) {
        // Setup our GL Environment so it renders correctly
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        GlStateManager._colorMask(true, true, true, true);

        // Apply the Position Color Shader program to render our vertices correctly
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        // Prepare the buffer
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        // Add the vertices to our buffer
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v1.x, v2.y, 0).color(color.getRGB()).next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v2.x, v2.y, 0).color(color.getRGB()).next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v2.x, v1.y, 0).color(color.getRGB()).next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v1.x, v1.y, 0).color(color.getRGB()).next();
        // Render the buffer
        tessellator.draw();

        // Reset the environment
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /**
     * Renders a quad at the specified location
     * @param matrices - the current matrix stack
     * @param v1 - top left corner of the quad
     * @param v2 - bottom right corner of the quad
     * @param scheme - color scheme to use to render the quad
     */
    public void box(MatrixStack matrices, Vec2f v1, Vec2f v2, BoxColorScheme scheme) {
        // Setup our GL Environment so it renders correctly
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        GlStateManager._colorMask(true, true, true, true);

        // Apply the Position Color Shader program to render our vertices correctly
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        // Prepare the buffer
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        // Add the vertices to our buffer
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v1.x, v2.y, 0).color(scheme.getBottomLeft().getRGB()).next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v2.x, v2.y, 0).color(scheme.getBottomRight().getRGB()).next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v2.x, v1.y, 0).color(scheme.getTopRight().getRGB()).next();
        bufferBuilder.vertex(matrices.peek().getPositionMatrix(), v1.x, v1.y, 0).color(scheme.getTopLeft().getRGB()).next();
        // Render the buffer
        tessellator.draw();

        // Reset the environment
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /*
    * Overloaded methods below render a box but take in different parameters.
    * */
    public void box(MatrixStack matrices, int x, int y, int width, int height, BoxColorScheme scheme) {
        box(matrices, new Vec2f(x, y), new Vec2f(x + width, y + height), scheme);
    }

    public void box(MatrixStack matrices, float x, float y, float width, float height, BoxColorScheme scheme) {
        box(matrices, new Vec2f(x, y), new Vec2f(x + width, y + height), scheme);
    }

    public void box(MatrixStack matrices, int x, int y, int width, int height, Color color) {
        box(matrices, new Vec2f(x, y), new Vec2f(x + width, y + height), color);
    }

    public void box(MatrixStack matrices, float x, float y, float width, float height, Color color) {
        box(matrices, new Vec2f(x, y), new Vec2f(x + width, y + height), color);
    }

    /*
     * Rendering Textures
     */
    /**
     * Renders a texture/image
     * @param matrices
     * @param glID - the GL id number of the texture in the environment
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void image(MatrixStack matrices, int glID, float x, float y, float width, float height) {
        RenderSystem.setShaderTexture(0, glID);
        drawTexture(matrices, (int) x, (int) y, 0, 0f, 0f, (int) width, (int) height, (int) width, (int) height);
    }

    /*
    *  RENDERING ROUNDED QUADS
    * */
    public void roundedQuad(MatrixStack matrices, Vec2f v1, Vec2f v2, float cornerRadius, Color color) {
        // Central Quad
        box(
                matrices, v1.add(cornerRadius), v2.add(-cornerRadius), color
        );

        // Top Quad
        box(
                matrices,
                new Vec2f(v1.x + cornerRadius, v1.y),
                new Vec2f(v2.x - cornerRadius, v1.y + cornerRadius),
                color
        );

        // Bottom Quad
        box(
                matrices,
                new Vec2f(v1.x + cornerRadius, v2.y - cornerRadius),
                new Vec2f(v2.x - cornerRadius, v2.y),
                color
        );

        // Left Quad
        box(
                matrices,
                new Vec2f(v1.x, v1.y + cornerRadius),
                new Vec2f(v1.x + cornerRadius, v2.y - cornerRadius),
                color
        );

        // Right Quad
        box(
                matrices,
                new Vec2f(v2.x - cornerRadius, v1.y + cornerRadius),
                new Vec2f(v2.x, v2.y - cornerRadius),
                color
        );

        // Top Left Corner
        roundCorner(
                matrices,
                new Vec2f(v1.x + cornerRadius, v1.y),
                new Vec2f(v1.x, v1.y + cornerRadius),
                color
        );

        // Top Right Corner
        roundCorner(
                matrices,
                new Vec2f(v2.x - cornerRadius, v1.y),
                new Vec2f(v2.x, v1.y + cornerRadius),
                color
        );

        // Bottom Left Corner
        roundCorner(
                matrices,
                new Vec2f(v1.x + cornerRadius, v2.y),
                new Vec2f(v1.x, v2.y - cornerRadius),
                color, false
        );

        // Bottom Right Corner
        roundCorner(
                matrices,
                new Vec2f(v2.x - cornerRadius, v2.y),
                new Vec2f(v2.x, v2.y - cornerRadius),
                color
        );
    }

    public void roundCorner(MatrixStack matrices, Vec2f v1, Vec2f v2, Color color) {
        roundCorner(matrices, v1, v2, color, true);
    }

    public void roundCorner(MatrixStack matrices, Vec2f v1, Vec2f v2, Color color, boolean reverse) {
        // Setup shader and rendering
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Vec2f center = new Vec2f(v1.x, v2.y);
        Vec2f control = new Vec2f(v2.x, v1.y);

        // Begin the triangle fan
        bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        // Add centre vertex to the buffer
        bufferBuilder.vertex(center.x, center.y, 0).color(color.getRGB()).next();
        // Gen list of vertices with a cubic bezier
        List<Vec2f> vertices = cubicBezierPoints(v1, v2, control);
        // Reverse if specified
        if (reverse) Collections.reverse(vertices);
        // Add all the points to the buffer
        vertices.forEach(v -> bufferBuilder.vertex(v.x, v.y, 0).color(color.getRGB()).next());
        // Render the buffer to screen
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    /**
     * Generate a list of points that follows a cubic bezier curve
     * @param start
     * @param end
     * @param middle
     * @return the list of points
     */
    public List<Vec2f> cubicBezierPoints(Vec2f start, Vec2f end, Vec2f middle) {
        LinkedList<Vec2f> points = new LinkedList<>();

        // Create the list of points with our equation
        for (float t = 0; t <= 1; t+=0.01) {
            float x = (1-t) * (1-t) * start.x + 2 * (1-t) * t * middle.x + t * t * end.x;
            float y = (1-t) * (1-t) * start.y + 2 * (1-t) * t * middle.y + t * t * end.y;
            points.add(new Vec2f(x, y));
        }

        // Get the 2 vectors
        Vec2f se = getVector(start, end);
        Vec2f sc = getVector(start, middle);

        // Check which way we are going
        // GL only draws in clockwise direction
        if (clockwiseAngle(se, sc) < 0) {
            reverse(points);
        }

        return points;
    }

    /**
     * Reverses a list
     * @param list
     * @param <T> Type of the list
     */
    public <T> void reverse(List<T> list) {
        // Create 2 pointers for the start and end
        int start = 0;
        int end = list.size() - 1;

        // Repeat while start is less
        while (start < end) {
            // Create a temp value to store our start value
            T temp = list.get(start);
            // Replace the start with the end value
            list.set(start, list.get(end));
            // Replace the end with the stored start value
            list.set(end, temp);
            // Increment/Decrement our pointers
            start++;
            end--;
        }

        // List is fully reversed!
    }

    /**
     * Works out the vector AB
     * @param a
     * @param b
     * @return
     */
    public Vec2f getVector(Vec2f a, Vec2f b) {
        return new Vec2f(b.x - a.x, b.y - a.y);
    }

    /**
     * Works out the clockwise angle between 2 vectors
     * @param a
     * @param b
     * @return
     */
    public double clockwiseAngle(Vec2f a, Vec2f b) {
        return Math.atan2(a.y, a.x) - Math.atan2(b.y, b.x);
    }
}
