package io.formulate.identity.entity;

import io.formulate.identity.model.PropertyView;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
public class Property {
  @Id @GeneratedValue private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(name = "time_zone", nullable = false)
  private String timeZone;

  @ManyToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;

  public Property(PropertyView propertyView, Tenant tenant) {
    this.name = propertyView.getName();
    this.timeZone = propertyView.getTimeZone();
    this.tenant = tenant;
  }

  public PropertyView toPropertyView() {
    return new PropertyView(id, name, timeZone);
  }

  public static List<PropertyView> toPropertyViews(List<Property> properties) {
    return Optional.ofNullable(properties)
        .map(propertyList -> propertyList.stream().map(Property::toPropertyView).toList())
        .orElse(null);
  }
}
