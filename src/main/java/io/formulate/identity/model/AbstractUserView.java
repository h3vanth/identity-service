package io.formulate.identity.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
  @JsonSubTypes.Type(value = TenantUserView.class, name = TenantUserView.TYPE),
  @JsonSubTypes.Type(value = IntegrationUserView.class, name = IntegrationUserView.TYPE)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractUserView {
  protected Long id;
  protected String email;
  protected String username;
  protected List<RoleView> roles;
  protected List<ProductView> products;
}
