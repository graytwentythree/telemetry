/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 9, 2016
 */

package sunseeker.telemetry;

import gnu.io.SerialPortEventListener;
import java.io.InputStream;

interface ListenerInterface extends SerialPortEventListener {
    public void setInputStream(InputStream stream);

    public void addObserver(ListenerObserverInterface observer);
}