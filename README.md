Keda is a Kubernetes autoscaling based on events

![Global Architecture](/images/architecture.png)

# Start up

Operator installation:

```bash
oc apply -f operator/openshift-gitops.yaml
```

Retrieve ArgoCD route: 

```bash
oc get route -A | grep openshift-gitops-server | awk '{print $3}'
```

Get the ArgoCD admin password: 

```bash
oc -n openshift-gitops get secret openshift-gitops-cluster -o json | jq -r '.data["admin.password"]' | base64 -d
```

ArgoCD needs some privileges to create specific resources. In this demo, we'll apply cluster-role to ArgoCD to avoid the fine-grain RBAC.

```bash
oc apply -f argocd/cluster-role.yaml
```

Now, we apply the bootstrap application:

```bash
oc apply -f argocd/argocd-app-bootstrap.yaml
```