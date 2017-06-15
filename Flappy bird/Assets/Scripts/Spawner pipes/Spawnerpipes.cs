using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spawnerpipes : MonoBehaviour {

    [SerializeField]
    private GameObject pipeHolder;

    // Use this for initialization
    void Start()
    {
        StartCoroutine(Spawner());
    }

    IEnumerator Spawner()
    {
        yield return new WaitForSeconds(3/2);
        Vector3 temp = pipeHolder.transform.position;
        temp.y = Random.Range(-2.5f, 2.5f);

        Instantiate(pipeHolder, temp, Quaternion.identity);
        StartCoroutine(Spawner());
    }

}
