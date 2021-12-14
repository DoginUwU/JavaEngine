package engine.light;

import org.joml.Vector3f;

public class Light {
    
        private Vector3f color;
        private Vector3f position;
        private Vector3f attenuation;
    
        public Light(Vector3f color, Vector3f position, Vector3f attenuation) {
            this.color = color;
            this.position = position;
            this.attenuation = attenuation;
        }
    
        public Light(Vector3f color, Vector3f position) {
            this(color, position, new Vector3f(1, 1, 1));
        }
    
        public Vector3f getColor() {
            return color;
        }
    
        public void setColor(Vector3f color) {
            this.color = color;
        }
    
        public Vector3f getPosition() {
            return position;
        }
    
        public void setPosition(Vector3f position) {
            this.position = position;
        }
    
        public Vector3f getAttenuation() {
            return attenuation;
        }
    
        public void setAttenuation(Vector3f attenuation) {
            this.attenuation = attenuation;
        }
}
