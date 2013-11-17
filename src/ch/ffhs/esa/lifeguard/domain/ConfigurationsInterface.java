package ch.ffhs.esa.lifeguard.domain;

import ch.ffhs.esa.lifeguard.persistence.TableGatewayInterface;

/**
 * Marker interface for the Configurations table gateway. Provided mainly to
 * benefit testability and bind a type to TableGateWayInterface's E parameter.
 * 
 * @author Juerg Gutknecht <juerg.gutknecht@students.ffhs.ch>
 *
 */
public interface ConfigurationsInterface
	extends TableGatewayInterface<ConfigurationInterface> {

}
