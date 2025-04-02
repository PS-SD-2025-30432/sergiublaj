import { Routes } from '@angular/router';
import { ROUTES } from './core/config/routes.enum';


export const routes: Routes = [
  {
    path: ROUTES.EMPTY,
    loadComponent: () => import('./core/components/home/home.component').then(m => m.HomeComponent),
  },
  {
    path: ROUTES.CHEFS,
    loadComponent: () => import('./feature/chefs/chefs/chefs.component').then(m => m.ChefsComponent),
  },
  {
    path: `${ROUTES.CHEFS}/:id`,
    loadComponent: () => import('./feature/chefs/chef/chef.component').then(m => m.ChefComponent),
  },
  {
    path: ROUTES.ABOUT,
    loadComponent: () => import('./core/components/about/about.component').then(m => m.AboutComponent),
  },
  {
    path: ROUTES.NOT_FOUND,
    loadComponent: () => import('./core/components/not-found/not-found.component').then(m => m.NotFoundComponent),
  },
  {
    path: ROUTES.ALL,
    loadComponent: () => import('./core/components/not-found/not-found.component').then(m => m.NotFoundComponent),
  },
];
