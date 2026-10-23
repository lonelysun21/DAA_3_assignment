
import csv
from pathlib import Path
import matplotlib.pyplot as plt

folder = Path(__file__).resolve().parent

with open(folder.parent / "results.csv", newline="") as file:
    rows = list(csv.DictReader(file))

algorithms = [
    "Kruskal",
    "Prim",
    "Kosaraju",
    "Kahn",
    "DagShortestPath"
]

plt.figure(figsize=(11, 7))

for algorithm in algorithms:
    for density in ["sparse", "dense"]:
        data = [
            row for row in rows
            if row["algorithm"] == algorithm
            and row["graph"].endswith(density)
        ]

        data.sort(key=lambda row: int(row["n"]))

        sizes = [int(row["n"]) for row in data]

        operations = [
            int(row["dsu_ops"])
            + int(row["edge_scans"])
            + int(row["comparisons"])
            for row in data
        ]

        plt.loglog(
            sizes,
            operations,
            marker="o",
            label=f"{algorithm} {density}"
        )

plt.xlabel("Number of vertices")
plt.ylabel("Operation count")
plt.title("Operations vs Graph Size")
plt.legend(fontsize=8)
plt.grid(True)
plt.tight_layout()

plt.savefig(folder / "operations_vs_n.png", dpi=150)
plt.close()
