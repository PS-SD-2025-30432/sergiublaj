import { Routes } from '@angular/router';
import { ROUTES } from '../../core/config/routes.enum';
import { hasAuthorization } from '../../core/guards/authorization/authorization.guard';


export const routes: Routes = [
  {
    path: ROUTES.EMPTY,
    loadComponent: () => import('./chefs/chefs.component').then(m => m.ChefsComponent),
    canActivate: [ hasAuthorization ],
    data: {
      requiredRoles: [ 'ADMIN', 'MODERATOR' ]
    }
  },
  {
    path: ROUTES.ID,
    loadComponent: () => import('./chef/chef.component').then(m => m.ChefComponent),
    canActivate: [ hasAuthorization ],
    data: {
      requiredRoles: [ 'ADMIN', 'MODERATOR' ],
      isSelf: true
    }
  },
  {
    path: ROUTES.ALL,
    redirectTo: ROUTES.EMPTY
  }
];
